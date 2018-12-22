package com.project.MyProject.converter;

public interface DtoEntityConverter<T, B> {
    T convertToDto(final B dbo);
    B convertToDbo(final T dto);
}
