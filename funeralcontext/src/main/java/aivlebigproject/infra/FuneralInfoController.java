// ========================================
// FILENAME: aivlebigproject/funeralcontext/src/main/java/aivlebigproject/domain/FuneralInfoController.java
// 역할 : 
// ========================================

package aivlebigproject.infra;

import aivlebigproject.domain.*;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/funeralInfos")
@Transactional
public class FuneralInfoController {

    @Autowired
    FuneralInfoRepository funeralInfoRepository;

    // [수정] updateFuneralInfo 메서드를 @RequestBody를 사용하도록 변경합니다.
    @RequestMapping(
        value = "/funeralInfos/{id}/updatefuneralinfo",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public FuneralInfo updateFuneralInfo(
        @PathVariable(value = "id") Long id,
        @RequestBody UpdateFuneralInfoCommand command
    ) throws Exception {
        System.out.println(
            "##### /funeralInfo/updateFuneralInfo  called #####"
        );
        Optional<FuneralInfo> optionalFuneralInfo = funeralInfoRepository.findById(
            id
        );

        optionalFuneralInfo.orElseThrow(() -> new Exception("No Entity Found"));
        FuneralInfo funeralInfo = optionalFuneralInfo.get();
        funeralInfo.updateFuneralInfo(command);

        funeralInfoRepository.save(funeralInfo);
        return funeralInfo;
    }

    @RequestMapping(
        value = "/funeralInfos/{id}/createobituary",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public FuneralInfo createObituary(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /funeralInfo/createObituary  called #####");
        Optional<FuneralInfo> optionalFuneralInfo = funeralInfoRepository.findById(
            id
        );

        optionalFuneralInfo.orElseThrow(() -> new Exception("No Entity Found"));
        FuneralInfo funeralInfo = optionalFuneralInfo.get();
        funeralInfo.createObituary();

        funeralInfoRepository.save(funeralInfo);
        return funeralInfo;
    }

    @RequestMapping(
        value = "/funeralInfos/{id}/createschedule",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public FuneralInfo createSchedule(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /funeralInfo/createSchedule  called #####");
        Optional<FuneralInfo> optionalFuneralInfo = funeralInfoRepository.findById(
            id
        );

        optionalFuneralInfo.orElseThrow(() -> new Exception("No Entity Found"));
        FuneralInfo funeralInfo = optionalFuneralInfo.get();
        funeralInfo.createSchedule();

        funeralInfoRepository.save(funeralInfo);
        return funeralInfo;
    }

    @RequestMapping(
        value = "/funeralInfos/{id}/createalldocuments",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public FuneralInfo createAllDocuments(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println(
            "##### /funeralInfo/createAllDocuments  called #####"
        );
        Optional<FuneralInfo> optionalFuneralInfo = funeralInfoRepository.findById(
            id
        );

        optionalFuneralInfo.orElseThrow(() -> new Exception("No Entity Found"));
        FuneralInfo funeralInfo = optionalFuneralInfo.get();
        funeralInfo.createAllDocuments();

        funeralInfoRepository.save(funeralInfo);
        return funeralInfo;
    }

    @RequestMapping(
        value = "/funeralInfos/{id}/createdeathreport",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public FuneralInfo createDeathReport(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println(
            "##### /funeralInfo/createDeathReport  called #####"
        );
        Optional<FuneralInfo> optionalFuneralInfo = funeralInfoRepository.findById(
            id
        );

        optionalFuneralInfo.orElseThrow(() -> new Exception("No Entity Found"));
        FuneralInfo funeralInfo = optionalFuneralInfo.get();
        funeralInfo.createDeathReport();

        funeralInfoRepository.save(funeralInfo);
        return funeralInfo;
    }
}
//>>> Clean Arch / Inbound Adaptor
