package ru.samarina.CourseProject.service;

import org.springframework.stereotype.Service;
import ru.samarina.CourseProject.dto.BookStoreInfoDto;
import ru.samarina.CourseProject.entity.BookStoreInfo;
import ru.samarina.CourseProject.repository.BookStoreInfoRepository;

import java.util.Optional;

@Service
public class BookStoreInfoServiceImpl implements BookStoreInfoService {

    private final BookStoreInfoRepository bookStoreInfoRepository;

    public BookStoreInfoServiceImpl(BookStoreInfoRepository bookStoreInfoRepository) {
        this.bookStoreInfoRepository = bookStoreInfoRepository;
    }

    @Override
    public void updateBookStoreInfo(BookStoreInfoDto bookStoreInfoDto) {
        // Поиск записи в репозитории
        Optional<BookStoreInfo> optionalInfo = bookStoreInfoRepository.findByBookIdAndStoreId(
                bookStoreInfoDto.getBookId(),
                bookStoreInfoDto.getStoreId()
        );

        BookStoreInfo info = optionalInfo.orElseThrow(() ->
                new RuntimeException("BookStoreInfo not found"));

        // Обновляем данные
        info.setPrice(bookStoreInfoDto.getPrice());
        info.setQuantity(bookStoreInfoDto.getQuantity());
        bookStoreInfoRepository.save(info);
    }
}
