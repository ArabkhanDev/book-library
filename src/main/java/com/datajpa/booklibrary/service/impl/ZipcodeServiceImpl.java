package com.datajpa.booklibrary.service.impl;

import com.datajpa.booklibrary.dto.requestDto.ZipcodeRequestDto;
import com.datajpa.booklibrary.model.City;
import com.datajpa.booklibrary.model.Zipcode;
import com.datajpa.booklibrary.repository.ZipcodeRepository;
import com.datajpa.booklibrary.service.inter.CityService;
import com.datajpa.booklibrary.service.inter.ZipcodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ZipcodeServiceImpl implements ZipcodeService {

    private final ZipcodeRepository zipcodeRepository;
    private final CityService cityService;

    public ZipcodeServiceImpl(ZipcodeRepository zipcodeRepository, CityService cityService) {
        this.zipcodeRepository = zipcodeRepository;
        this.cityService = cityService;
    }

    @Transactional
    @Override
    public Zipcode addZipcode(ZipcodeRequestDto zipcodeRequestDto) {
        Zipcode zipcode = new Zipcode();
        zipcode.setName(zipcodeRequestDto.getName());
        if (zipcodeRequestDto.getCityId() == null){
            return zipcodeRepository.save(zipcode);
        }
        City city = cityService.getCity(zipcodeRequestDto.getCityId());
        zipcode.setCity(city);
        return zipcodeRepository.save(zipcode);

    }

    @Override
    public List<Zipcode> getZipcodes() {
//        return new ArrayList<>(zipcodeRepository.findAll());
        return StreamSupport
                .stream(zipcodeRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }

    @Override
    public Zipcode getZipcode(Long zipcodeId) {
        return zipcodeRepository.findById(zipcodeId)
                .orElseThrow(()->new IllegalArgumentException("zipcode with id: " + zipcodeId + "could not be found"));
    }

    @Override
    public Zipcode deleteZipcode(Long zipcodeId) {
        Zipcode zipcode = getZipcode(zipcodeId);
        zipcodeRepository.delete(zipcode);
        return zipcode;
    }

    @Override
    public Zipcode editZipcode(Long zipcodeId, ZipcodeRequestDto zipcodeRequestDto) {
            Zipcode zipcodeToEdit = getZipcode(zipcodeId);
            zipcodeToEdit.setName(zipcodeRequestDto.getName());
            if (zipcodeRequestDto.getCityId() == null){
                return zipcodeToEdit;
            }
            City city = cityService.getCity(zipcodeRequestDto.getCityId());
            zipcodeToEdit.setCity(city);
            return zipcodeRepository.save(zipcodeToEdit);
    }

    @Transactional
    @Override
    public Zipcode addCityToZipcode(Long zipcodeId, Long cityId) {
        Zipcode zipcode = getZipcode(zipcodeId);
        City city = cityService.getCity(cityId);
        if(Objects.nonNull(zipcode.getCity())){
            throw new IllegalArgumentException("zipcode already has a city");
        }
        zipcode.setCity(city);
        return zipcode;
    }

    @Transactional
    @Override
    public Zipcode removeCityFromZipcode(Long zipcodeId) {
        Zipcode zipcode = getZipcode(zipcodeId);
        if(!Objects.nonNull(zipcode.getCity())){
            throw new IllegalArgumentException("zipcode does not have a city");
        }
        zipcode.setCity(null);
        return zipcode;

    }
}
