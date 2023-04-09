package dnd.auction.domain.auction.service;

import dnd.auction.domain.auction.entity.Classes;
import dnd.auction.domain.auction.repository.ClassesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassesWriteService {

    final private ClassesRepository classesRepository;

    public Classes registerClasses(String classes){
        return classesRepository.save(Classes.builder().name(classes).build());
    }
}
