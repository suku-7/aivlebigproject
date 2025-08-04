# ========================================
# FILENAME: aivlebigproject/funeralcontext-ai/services/obituary.py
# 역할 : AI+RAG를 이용해 템플릿을 선택하고 부고 이미지를 생성하여 Azure에 업로드
# ========================================

import io
import os
import pandas as pd
from openai import OpenAI
from PIL import Image, ImageDraw, ImageFont
from datetime import datetime
import qrcode
import urllib.parse
from app.schemas import ObituaryDataCreated
from .azure_uploader import upload_to_blob # [주석] Azure 업로드 함수를 import 합니다.

# =======================================================================
# .env 파일에 OPENAI_API_KEY="sk-..." 형식으로 키를 저장해야 합니다.
# =======================================================================
client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))

# =======================================================================
# AI를 이용한 템플릿 선택 함수 (RAG)
# =======================================================================
def select_template_id_with_gpt(user_keyword, template_df):
    """
    사용자 키워드와 템플릿 목록을 GPT에 보내 가장 적합한 템플릿 ID를 받아옵니다.
    """
    print(f"\n🤖 GPT에게 '{user_keyword}' 키워드에 가장 어울리는 템플릿을 물어봅니다...", flush=True)
    
    template_list_str = "\n".join([f"{row['id']}: {row['keyword']}" for index, row in template_df.iterrows()])
    
    messages = [
        {"role": "system", "content": "당신은 장례 문서 템플릿을 추천하는 AI입니다. 사용자의 키워드와 가장 잘 어울리는 템플릿을 목록에서 하나만 골라, 오직 해당 번호(숫자)만 대답해주세요."},
        {"role": "user", "content": f"[사용자 키워드]\n{user_keyword}\n\n[템플릿 목록]\n{template_list_str}"}
    ]
    
    try:
        response = client.chat.completions.create(
            model="gpt-4.1-mini", messages=messages, max_tokens=5, temperature=0.0
        )
        selected_id = int(response.choices[0].message.content.strip())
        print(f"🧠 GPT의 선택: {selected_id}번", flush=True)
        return selected_id
    except Exception as e:
        print(f"❌ GPT API 호출 중 오류 발생: {e}", flush=True)
        print("⚠️ 기본 템플릿(1번)을 대신 사용합니다.", flush=True)
        return 1 # 오류 발생 시 기본 템플릿 ID 반환

# =======================================================================
# 메인 부고장 생성 함수
# =======================================================================
def create_obituary_document(event_data: ObituaryDataCreated, blob_service_client, container_name: str) -> dict:

    try:
        # --- [추가] 1. RAG를 위한 템플릿 정보 로드 ---
        # templates.csv 파일은 resources/templates/ 폴더에 위치해야 합니다.
        templates_csv_path = "resources/templates/templates.csv"
        templates_df = pd.read_csv(templates_csv_path)

        # --- [추가] 2. AI를 이용해 템플릿 선택 ---
        user_keyword = event_data.templateKeyword or "기본"
        selected_id = select_template_id_with_gpt(user_keyword, templates_df)
        
        # 선택된 ID에 해당하는 템플릿 파일 경로를 가져옵니다.
        template_path = templates_df[templates_df['id'] == selected_id]['file_path'].iloc[0]
        print(f"📄 선택된 템플릿: {template_path}", flush=True)
        
        # --- 3. 이미지 생성 준비 ---
        font_path = "resources/fonts/NanumGothic.ttf"
        
        font_title = ImageFont.truetype(font_path, size=70)
        font_main = ImageFont.truetype(font_path, size=26)
        font_list = ImageFont.truetype(font_path, size=24)
        font_small = ImageFont.truetype(font_path, size=20) # [주석] 작은 폰트 추가

        # --- 4. 텍스트 내용 가공 ---
        procession_date_with_day = event_data.processionDateTime
        if event_data.processionDateTime:
            try:
                # [수정] ISO 형식의 날짜/시간 문자열을 datetime 객체로 변환합니다.
                dt_obj = datetime.fromisoformat(event_data.processionDateTime.replace('Z', '+00:00'))
                
                # [수정] 변환된 객체를 사용해 원하는 형식의 문자열로 만듭니다.
                weekdays = ['월', '화', '수', '목', '금', '토', '일']
                day_of_week = weekdays[dt_obj.weekday()]
                procession_date_with_day = dt_obj.strftime(f'%Y년 %m월 %d일({day_of_week}) %H시 %M분')

            except (ValueError, IndexError) as e:
                print(f"⚠️ 발인 날짜 파싱 오류: {e}. 원본 데이터 '{event_data.processionDateTime}'를 그대로 사용합니다.", flush=True)
            
        # --- 5. 이미지에 텍스트 쓰기 ---
        image = Image.open(template_path).convert("RGBA")
        draw = ImageDraw.Draw(image)
        text_color = (50, 50, 50)
        
        # [주석] 새로운 섹션 추가에 따라 좌표를 전체적으로 재조정했습니다.
        coordinates = {
            "title": (image.width / 2, 200),
            "intro": (image.width / 2, 320),
            "details_list": (170, 450),
            "account_info": (170, 650), # [주석] 계좌 정보 좌표 추가
            "closing": (image.width / 2, 850),
            "signature": (image.width / 2, 950)
        }

        # 제목
        draw.text(coordinates['title'], "【 訃 告 】", font=font_title, fill=text_color, anchor="mm")

        # 서문
        deceased_date_obj = None
        if event_data.deceasedDate:
            if isinstance(event_data.deceasedDate, str):
                deceased_date_obj = datetime.fromisoformat(event_data.deceasedDate.replace('Z', ''))
            else:
                deceased_date_obj = event_data.deceasedDate
        
        date_simple_str = f"{deceased_date_obj.month}월 {deceased_date_obj.day}일" if deceased_date_obj else "정보 없음"
        
        intro_text_line1 = f"故 {event_data.deceasedName} 님께서 {date_simple_str} 별세하셨음을 삼가 알려 드립니다."
        intro_text_line2 = "가시는 길 깊은 애도와 명복을 빌어주시길 진심으로 바랍니다."
        intro_text = f"{intro_text_line1}\n{intro_text_line2}"
        draw.text(coordinates['intro'], intro_text, font=font_main, fill=text_color, spacing=10, align="center", anchor="mm")

        # 핵심 정보 목록
        details_text = f"""
■ 상주 : {event_data.chiefMourners or '정보 없음'}
■ 빈소 : {event_data.funeralHomeName} {event_data.mortuaryInfo or ''}
■ 발인 : {procession_date_with_day or '정보 없음'}
■ 장지 : {event_data.burialSiteInfo or '정보 없음'}
        """.strip()
        draw.text(coordinates['details_list'], details_text, font=font_list, fill=text_color, spacing=15)

        # [주석] 조문객의 편의를 위해 계좌번호 안내 섹션을 추가했습니다.
        # 마음 전하실 곳 (계좌 정보)
        if event_data.chiefMournerAccountNumber:
            account_info_text = f"""
■ 마음 전하실 곳
  {event_data.chiefMournerBankName or ''} {event_data.chiefMournerAccountNumber or ''}
  (예금주: {event_data.chiefMournerAccountHolder or ''})
            """.strip()
            draw.text(coordinates['account_info'], account_info_text, font=font_list, fill=text_color, spacing=15)

        # 맺음말
        # closing_text = "경황이 없어 일일이 연락드리지 못함을\n널리 혜량하여 주시기 바랍니다."
        # draw.text(coordinates['closing'], closing_text, font=font_main, fill=text_color, spacing=10, align="center", anchor="mm")

        # [주석] 유가족 개인 연락처 대신 장례지도사 정보를 넣어 문의 창구를 일원화했습니다.
        # 서명 및 연락처
        signature_text = f"- {event_data.chiefMourners} 올림 -\n장례 문의 : {event_data.directorName} ({event_data.directorPhone})"
        draw.text(coordinates['signature'], signature_text, font=font_list, fill=text_color, spacing=10, align="center", anchor="mm")

        # [주석] 장례식장 주소를 기반으로 길찾기 QR코드를 생성하고 이미지 우측 하단에 삽입합니다.
        if event_data.funeralHomeAddress:
            encoded_address = urllib.parse.quote(event_data.funeralHomeAddress)
            qr_data = f"https://map.naver.com/p/search/{encoded_address}"
            
            qr_img = qrcode.make(qr_data)
            qr_img = qr_img.resize((120, 120))
            
            qr_position = (image.width - qr_img.width - 80, image.height - qr_img.height - 80)
            image.paste(qr_img, qr_position)
            draw.text((qr_position[0] + qr_img.width / 2, qr_position[1] + qr_img.height + 10), 
                      "오시는 길 (QR)", font=font_small, fill=text_color, anchor="mt")

        # 5. 완성된 이미지를 메모리상의 바이트 데이터로 변환
        img_byte_arr = io.BytesIO()
        image.save(img_byte_arr, format='PNG')
        file_data = img_byte_arr.getvalue()
        
        # 6. Azure에 업로드
        doc_id = event_data.obituaryId
        blob_name = f"obituaries/obituary_{doc_id}.png"
        file_url = upload_to_blob(blob_service_client, container_name, blob_name, file_data)

        if file_url:
            # 7. 성공 시, 파일 이름과 URL을 담은 딕셔너리 반환
            return {
                "fileName": blob_name,
                "fileUrl": file_url
            }
        else:
            return None

    except Exception as e:
        print(f"❌ 부고 이미지 생성 중 오류 발생: {e}", flush=True)
        return None