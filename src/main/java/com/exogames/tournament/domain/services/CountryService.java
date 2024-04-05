package com.exogames.tournament.domain.services;

import com.exogames.tournament.domain.dtos.CountryDto;
import com.exogames.tournament.domain.dtos.CreateCountryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountryService {

    CountryDto createCountry (CreateCountryDto createCountryDto);
    List<CountryDto> getAllCountries();
    List<CountryDto> getAllActiveCountries();
    CountryDto getCountryById(String id);
    CountryDto updateCountry(CountryDto countryDto);
    void deactivateCountry(String id);
    void deleteCountry(String id);
}
