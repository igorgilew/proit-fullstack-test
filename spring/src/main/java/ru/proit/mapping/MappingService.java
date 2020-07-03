package ru.proit.mapping;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.proit.dto.organization.OrganizationDto;
import ru.proit.dto.worker.WorkerDto;
import ru.proit.spring.generated.tables.pojos.Organization;
import ru.proit.spring.generated.tables.pojos.Worker;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MappingService {
    private ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        //Дополнительные настройки.
    }

    public <S, D> D map(S source, Class<D> clazz) {
        return modelMapper.map(source, clazz);
    }

    public <S, D> void map(S source, D dest) {
        modelMapper.map(source, dest);
    }

    public <S, D> List<D> mapList(List<S> sources, Class<D> clazz){
        return sources.stream()
                .map(s -> map(s, clazz))
                .collect(Collectors.toList());
    }

    public OrganizationDto map(Organization source){
        OrganizationDto org = new OrganizationDto();
        org = modelMapper.map(source, OrganizationDto.class);
        org.setHead(new OrganizationDto());
        org.getHead().setIdd(source.getHeadIdd());
        return org;
    }

    public WorkerDto map(Worker source) {
        WorkerDto dto = modelMapper.map(source, WorkerDto.class);
        dto.setOrgIdd(new OrganizationDto());
        dto.getOrgIdd().setIdd(source.getOrgIdd());
        dto.setBossIdd(new WorkerDto());
        dto.getBossIdd().setIdd(source.getBossIdd());
        return dto;
    }

}
