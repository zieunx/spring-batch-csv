package com.danal.publicdataprocessor.batch.writer;

import com.danal.publicdataprocessor.domain.model.Restaurant;
import com.danal.publicdataprocessor.domain.repository.RestaurantRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class RestaurantItemWriter implements ItemWriter<Restaurant> {

    private final RestaurantRepository restaurantRepository;

    public RestaurantItemWriter(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void write(Chunk<? extends Restaurant> chunk) {
        // Chunk 내부 데이터를 저장
        restaurantRepository.saveAll(chunk.getItems());
    }
}
