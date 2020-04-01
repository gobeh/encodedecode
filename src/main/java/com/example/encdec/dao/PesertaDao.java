package com.example.encdec.dao;

import com.example.encdec.entity.Peserta;
import com.sofwan.latspring.entity.Peserta;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PesertaDao extends PagingAndSortingRepository<Peserta, String>{


}