package com.project.MyProject.converter;

import java.util.HashSet;
import java.util.Set;

public interface DtoEntityConverter<T, B> {

    T convertToDto(final B dbo);

    B convertToDbo(final T dto);

    default Set<T> convertToDto(final Set<B> entitySet){
        if (entitySet != null){
            final Set<T> dtoSet = new HashSet<>();
            for (final B entity : entitySet){
                final T dto = convertToDto(entity);
                dtoSet.add(dto);
            }
            return dtoSet;
        }
        return null;
    }

    default Set<B> convertToDbo(final Set<T> dtoSet){
        if (dtoSet != null){
            final Set<B> entitySet = new HashSet<>();
            for (final T dto : dtoSet){
                final B entity = convertToDbo(dto);
                entitySet.add(entity);
            }
            return entitySet;
        }
        return null;
    }


}
