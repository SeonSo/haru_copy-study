package com.project1.haruco.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public class RepositoryHelper {

    public static <T> Slice<T> toSlice(final List<T> contents, final Pageable pageable){
        final boolean hasNext = isContentSizeGreaterThanPageSize(contents, pageable);

        return new SliceImpl<>(hasNext ? subListLastContent(contents, pageable) : contents, pageable, hasNext);
    }

    private static <T> boolean isContentSizeGreaterThanPageSize(final List<T> contents, final Pageable pageable){
        return pageable.isPaged() && contents.size() > pageable.getPageSize();
    }

    private static <T> List<T> subListLastContent(final List<T> contents, final Pageable pageable){
        return contents.subList(0, pageable.getPageSize());
    }
}
