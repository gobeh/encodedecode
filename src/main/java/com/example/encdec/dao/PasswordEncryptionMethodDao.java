package com.example.encdec.dao;

import com.example.encdec.entity.Peserta;
import com.example.encdec.entity.system.PasswordEncryptionMethod;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PasswordEncryptionMethodDao extends PagingAndSortingRepository<PasswordEncryptionMethod, String>{

    PasswordEncryptionMethod findFirstByAktifTrueOrderByIdAsc();
}