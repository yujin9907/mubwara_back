package site.metacoding.finals.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.exception.RuntimeApiException;
import site.metacoding.finals.domain.commercial.Commercial;
import site.metacoding.finals.domain.commercial.CommercialRepository;
import site.metacoding.finals.dto.commercial.CommercialRespDto.CommercialListRespDto;

@RequiredArgsConstructor
@Service
public class CommercialService {

    private final CommercialRepository commercialRepository;

    public List<CommercialListRespDto> listCommercial() {
        List<Commercial> commercials = commercialRepository.findAll();
        if (commercials.size() == 0) {
            throw new RuntimeApiException("광고가 없습니다.", HttpStatus.NOT_FOUND);
        }

        return commercials.stream().map((c) -> new CommercialListRespDto(c)).collect(Collectors.toList());
    }

}
