package com.ajoubooking.demo;

import com.ajoubooking.demo.domain.Bookshelf;
import com.ajoubooking.demo.dto.CallNumberDto;
import com.ajoubooking.demo.dto.ColumnAddressResponseDto;
import com.ajoubooking.demo.repository.BookshelfRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class BookshelfRepositoryTest {
    private final BookshelfRepository bookshelfRepository;

    @Autowired
    public BookshelfRepositoryTest(BookshelfRepository bookshelfRepository) {
        this.bookshelfRepository = bookshelfRepository;
    }

    @Test
    @DisplayName("입력된 값보다 작거나 같은 값 중 DB 상에서 가장 큰 값을 리턴하는 테스트") // 값 에러라서 ㄱㅊ
    void findFirstByStartCallNumberClassificationNumberLessThanEqualOrderByClassificationNumberDescTest() {
        Bookshelf foundRow = bookshelfRepository
                .findFirstByStartCallNumberClassificationNumberLessThanEqualOrderByStartCallNumberClassificationNumberDesc(BigDecimal.valueOf(5.18));

        assertThat(foundRow.getStartCallNumber().getClassificationNumber()).isEqualTo(BigDecimal.valueOf(5.136000));
    }

    @Test
    @DisplayName("classification-number를 통한 조회")
    void findAllByStartCallNumberClassificationNumberTest() {
        List<Bookshelf> bookshelfList = bookshelfRepository.findByStartCallNumberClassificationNumber(BigDecimal.valueOf(5.136));
        Bookshelf temp = bookshelfList.get(0);

        ColumnAddressResponseDto testVal = ColumnAddressResponseDto.builder()
                        .category(temp.getColumnAddress().getCategory())
                        .bookshelfNum(temp.getColumnAddress().getBookshelfNum())
                        .columnNum(temp.getColumnAddress().getColumnNum())
                        .build();
        ColumnAddressResponseDto answer = ColumnAddressResponseDto.builder()
                .category(0)
                .bookshelfNum(5)
                .columnNum(8)
                .build();

        assertThat(testVal).usingRecursiveComparison().isEqualTo(answer);
    }

}
