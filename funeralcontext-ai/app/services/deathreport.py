# ========================================
# FILENAME: aivlebigproject/funeralcontext-ai/services/deathreport.py
# 역할 : 사망신고서 PDF를 생성하여 Azure에 업로드
# ========================================

import fitz
from datetime import datetime
from app.schemas import DeathReportDataCreated
from .azure_uploader import upload_to_blob # [주석] Azure 업로드 함수를 import 합니다.

def create_death_report_document(event_data: DeathReportDataCreated, blob_service_client, container_name: str) -> dict:
    """
    DeathReportDataCreated 이벤트 데이터를 받아 사망신고서 PDF를 생성하고,
    수정된 PDF 문서 객체를 반환합니다.
    """
    try:
        template_path = "resources/templates/blank_singo.pdf"
        font_path = "resources/fonts/NanumGothic.ttf"
        text_color = (0.2, 0.6, 0.9)

        # [주석] schemas.py의 최종 속성명에 맞춰 key 이름을 수정/추가했습니다.
        coordinates = {
            # 1. 신고일
            "declaration_year": (123, 93), "declaration_month": (173, 93), "declaration_day": (213, 93),
            
            # 2. 사망자
            "deceasedName": (200, 112), "deceasedNameHanja": (200, 140),
            "deceasedRrn": (428, 127),
            "checkbox_deceased_male": (309, 136), "checkbox_deceased_female": (337, 136),
            "deceasedRegisteredAddress": (165, 168),
            "deceasedAddress": (165, 198),
            "deceasedRelationToHouseholdHead": (455, 198),

            # 3. 사망일시 및 장소
            "death_year": (162, 227), "death_month": (204, 227), "death_day": (231, 227),
            "death_hour": (258, 227), "death_minute": (286, 227),
            "deathLocation": (190, 255), # [주석] funeralHomeAddress 대신 deathLocation을 사용합니다.
            "checkbox_deathLocationType_1": (190, 278), # 주택
            "checkbox_deathLocationType_2": (263, 278), # 의료기관
            "checkbox_deathLocationType_3": (354, 278), # 사회복지시설
            "checkbox_deathLocationType_4": (190, 291), # 공공시설
            "checkbox_deathLocationType_5": (338, 291), # 도로
            "checkbox_deathLocationType_6": (384, 291), # 상업, 서비스시설
            "checkbox_deathLocationType_7": (190, 303), # 산업장
            "checkbox_deathLocationType_8": (263, 303), # 농장
            "checkbox_deathLocationType_9": (420, 303), # 병원 이송 중 사망
            "checkbox_deathLocationType_10": (190, 316), # 기타
            "deathLocationEtc": (228, 318),
            "deathReportEtc": (165, 342),
            
            # 4. 신고인
            "reporterName": (237, 370), "reporterRrn": (424, 371),
            "checkbox_reporterQualification_1": (154, 394), # 동거하는 친족
            "checkbox_reporterQualification_2": (212, 394), # 동거하지 않는 친족
            "checkbox_reporterQualification_3": (280, 394), # 동거자
            "checkbox_reporterQualification_4": (155, 416), # 기타(보호시설장/사망장소관리자)
            "reporterRelationToDeceased": (393, 397),
            "reporterAddress": (148, 445), "reporterPhone": (369, 446),
            "reporterEmail": (468, 446),
            
            # 5. 제출인
            "submitterName": (195, 477), "submitterRrn": (424, 478),
        }

        doc = fitz.open(template_path)
        page = doc[0]

        with open(font_path, "rb") as fontfile:
            font_buffer = fontfile.read()
        page.insert_font(fontname="korean_font", fontbuffer=font_buffer)

        # [주석] schemas.py에 정의된 모든 속성을 event_data로부터 받아오도록 수정했습니다.
        data_to_fill = {
            "deceasedName": event_data.deceasedName,
            "deceasedNameHanja": event_data.deceasedNameHanja,
            "deceasedRrn": event_data.deceasedRrn,
            "deceasedRelationToHouseholdHead": event_data.deceasedRelationToHouseholdHead,
            "deceasedRegisteredAddress": event_data.deceasedRegisteredAddress,
            "deceasedAddress": event_data.deceasedAddress,
            "reporterName": event_data.reporterName,
            "reporterRrn": event_data.reporterRrn,
            "reporterRelationToDeceased": event_data.reporterRelationToDeceased,
            "reporterAddress": event_data.reporterAddress,
            "reporterPhone": event_data.reporterPhone,
            "reporterEmail": event_data.reporterEmail,
            "submitterName": event_data.submitterName,
            "submitterRrn": event_data.submitterRrn,
            "deathLocation": event_data.deathLocation,
            "deathLocationEtc": event_data.deathLocationEtc,
            "deathReportEtc": event_data.deathReportEtc,
        }
        
        # 날짜/시간 데이터 처리
        if event_data.reportRegistrationDate:
            dt_obj = event_data.reportRegistrationDate
            if isinstance(dt_obj, str):
                dt_obj = datetime.fromisoformat(dt_obj.replace('Z', ''))
            data_to_fill["declaration_year"] = dt_obj.strftime("%Y")
            data_to_fill["declaration_month"] = dt_obj.strftime("%m")
            data_to_fill["declaration_day"] = dt_obj.strftime("%d")

        if event_data.deceasedDate:
            dt_obj = event_data.deceasedDate
            if isinstance(dt_obj, str):
                dt_obj = datetime.fromisoformat(dt_obj.replace('Z', ''))
            data_to_fill["death_year"] = dt_obj.strftime("%Y")
            data_to_fill["death_month"] = dt_obj.strftime("%m")
            data_to_fill["death_day"] = dt_obj.strftime("%d")
            data_to_fill["death_hour"] = dt_obj.strftime("%H")
            data_to_fill["death_minute"] = dt_obj.strftime("%M")
        
        # 텍스트 필드 채우기
        for key, coord in coordinates.items():
            if "checkbox" not in key and key in data_to_fill and data_to_fill[key] is not None:
                page.insert_text(coord, str(data_to_fill[key]), fontname="korean_font", fontsize=10, color=text_color)

        # --- 체크박스 처리 ---
        # 1. 사망자 성별
        if event_data.deceasedGender == "남성": # 또는 "1"
            rect = fitz.Rect(coordinates["checkbox_deceased_male"], coordinates["checkbox_deceased_male"]) + (-4, -4, 4, 4)
            page.draw_oval(rect, color=text_color, width=1.5)
        elif event_data.deceasedGender == "여성": # 또는 "2"
            rect = fitz.Rect(coordinates["checkbox_deceased_female"], coordinates["checkbox_deceased_female"]) + (-4, -4, 4, 4)
            page.draw_oval(rect, color=text_color, width=1.5)
            
        # [주석] 주석을 해제하고 실제 데이터를 사용하도록 수정했습니다.
        # 2. 사망장소 구분
        if event_data.deathLocationType:
            checkbox_key = f"checkbox_deathLocationType_{event_data.deathLocationType}"
            if checkbox_key in coordinates:
                rect = fitz.Rect(coordinates[checkbox_key], coordinates[checkbox_key]) + (-4, -4, 4, 4)
                page.draw_oval(rect, color=text_color, width=1.5)

        # [주석] 주석을 해제하고 실제 데이터를 사용하도록 수정했습니다.
        # 3. 신고인 자격
        if event_data.reporterQualification:
            checkbox_key = f"checkbox_reporterQualification_{event_data.reporterQualification}"
            if checkbox_key in coordinates:
                rect = fitz.Rect(coordinates[checkbox_key], coordinates[checkbox_key]) + (-4, -4, 4, 4)
                page.draw_oval(rect, color=text_color, width=1.5)
        
        # --- [수정] Azure Blob 업로드 및 결과 반환 ---
        
        # 1. 완성된 PDF를 메모리상의 바이트 데이터로 변환
        file_data = doc.tobytes()
        doc.close() # 문서를 닫아 리소스를 해제합니다.
        
        # 2. Azure에 업로드
        doc_id = event_data.deathReportId
        blob_name = f"death-reports/death_report_{doc_id}.pdf"
        file_url = upload_to_blob(blob_service_client, container_name, blob_name, file_data)

        if file_url:
            # 3. 성공 시, 파일 이름과 URL을 담은 딕셔너리 반환
            return {
                "fileName": blob_name,
                "fileUrl": file_url
            }
        else:
            return None

    except Exception as e:
        print(f"❌ 사망신고서 PDF 생성 중 오류 발생: {e}", flush=True)
        return None