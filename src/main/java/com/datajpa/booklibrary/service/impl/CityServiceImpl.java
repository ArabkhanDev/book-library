package com.datajpa.booklibrary.service.impl;

import com.datajpa.booklibrary.dto.requestDto.CityRequestDto;
import com.datajpa.booklibrary.model.City;
import com.datajpa.booklibrary.repository.CityRepository;
import com.datajpa.booklibrary.service.inter.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City addCity(CityRequestDto cityRequestDto) {
        City city = new City();
        city.setName(cityRequestDto.getName());
        return cityRepository.save(city);
    }

    @Override
    public List<City> getCities() {
        List<City> cities = new ArrayList<>();
        cityRepository.findAll().forEach(city -> cities.add(city));
        return cities;

    }

    @Override
    public City getCity(Long cityId) {
        return cityRepository.findById(cityId)
                .orElseThrow(()->new IllegalArgumentException("city with cityId"+cityId+"could not be found"));
    }

    @Override
    public City deleteCity(Long cityId) {
        City city = getCity(cityId);
        cityRepository.delete(city);
        return city;
    }

    @Transactional
    @Override
    public City editCity(Long cityId, CityRequestDto cityRequestDto) {
        City cityEdit = getCity(cityId);
        cityEdit.setName(cityRequestDto.getName());
        return cityEdit;
    }
}
