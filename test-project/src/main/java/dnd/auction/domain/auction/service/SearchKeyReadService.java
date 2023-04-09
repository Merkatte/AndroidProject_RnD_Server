package dnd.auction.domain.auction.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dnd.auction.domain.auction.dto.response.ItemResponseDTO;
import dnd.auction.domain.auction.dto.response.SearchKeyApiResponseDTO;
import dnd.auction.domain.auction.entity.*;
import dnd.auction.domain.auction.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SearchKeyReadService {
    private final ItemTypeRepository itemTypeRepository;
    private final ItemPartsRepository itemPartsRepository;
    private final ItemRarityRepository itemRarityRepository;
    private final ClassesRepository classesRepository;
    private final ItemRepository itemRepository;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;
    public List<ItemType> getItemType(){
        return itemTypeRepository.findAll();
    }

    public List<ItemParts> getItemParts(){
        return itemPartsRepository.findAll();
    }

    public List<ItemRarity> getItemRarity(){
        return itemRarityRepository.findAll();
    }

    public List<Classes> getClasses() { return classesRepository.findAll(); }

    public List<ItemResponseDTO> getItems(){
        var items = itemRepository.findAll();
        System.out.println(items.size());
        return items.stream().map(ItemResponseDTO::new).toList();
    }

    public String getSearchKeyApi() throws JsonProcessingException {

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String cachedData = ops.get("searchKeyApi");
        if (cachedData != null){
            return cachedData;
        }

        List<ItemType> itemTypes = itemTypeRepository.findAll();
        List<ItemParts> itemParts = itemPartsRepository.findAll();
        List<ItemRarity> itemRarities = itemRarityRepository.findAll();
        List<Item> items = itemRepository.findAll();
        SearchKeyApiResponseDTO searchKeyApi =  new SearchKeyApiResponseDTO(itemParts, itemTypes, itemRarities, items);

        String jsonData = objectMapper.writeValueAsString(searchKeyApi);
        ops.set("searchKeyApi", jsonData, 1, TimeUnit.MINUTES);

        return jsonData;
    }
}
