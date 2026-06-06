package com.beautyhub.beautyhubbackend.dto.mapper;

import com.beautyhub.beautyhubbackend.domain.AbstractDomain;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractMapper
        <T extends AbstractDomain, D> {

    public abstract D toDTO(T entity);

    public abstract T toEntity(Object requestDTO);

    public List<D> toDTOList(List<T> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}