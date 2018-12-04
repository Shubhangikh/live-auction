package com.bidbig.items.service;

import com.bidbig.items.dto.ItemDto;
import com.bidbig.items.entity.Item;
import com.bidbig.items.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    public void createItem(ItemDto itemDto, String imageUri) {
        Item item = Item.builder()
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .minBidPrice(itemDto.getMinBidPrice())
                .imageLocation(imageUri)
                .status((short)1)
                .userId(itemDto.getUserId())
                .created(new Timestamp(new Date().getTime()))
                .modified(new Timestamp(new Date().getTime()))
                .build();

        itemRepository.saveAndFlush(item);
    }

    public void updateItem(Integer itemId, ItemDto itemDto, String imageUri) {
        Item item = itemRepository.getOne(itemId);
        if(item != null) {
            item.setName(itemDto.getName());
            item.setDescription(itemDto.getDescription());
            item.setMinBidPrice(itemDto.getMinBidPrice());
            item.setImageLocation(imageUri);
            item.setModified(new Timestamp(new Date().getTime()));
        }

    }

    public boolean deleteItem(Integer itemId) {
        Item item = itemRepository.getOne(itemId);
        if(item.getStatus() == 1) {
            return false;
        }
        itemRepository.delete(item);
        return true;
    }

    public Page<Item> getPageOfItems(Integer userId, Pageable pageable) {
        return itemRepository.findAllByUserId(userId, pageable);
    }
}
