package todoapp.commons.util;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * {@link Stream} 지원용 유틸리티
 *
 * @author springrunner.kr@gmail.com
 */
public interface StreamUtils {

    /**
     * {@link Iterator}로 {@link Stream}을 생성한다.
     *
     * @param iterator null 값이 아닌 이터레이터 개체
     * @return
     */
    static <T> Stream<T> createStreamFromIterator(Iterator<T> iterator) {
        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator, Spliterator.NONNULL);
        return StreamSupport.stream(spliterator, false);
    }

}
