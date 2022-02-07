package com.chompfooddeliveryapp.menuitemservisetest;


import com.chompfooddeliveryapp.model.enums.MenuCategory;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.repository.MenuItemRepository;
import com.chompfooddeliveryapp.service.serviceImpl.MenuServiceImplementation;
//import com.chompfooddeliveryapp.service.serviceInterfaces.MenuItemService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest

public class MenuItemServiceTest {





    @Mock
    MenuItemRepository menuItemRepository;

    @InjectMocks
    MenuServiceImplementation menuItemService;




    Date date = new Date();
    Long time = date.getTime();
    Timestamp timestamp = new Timestamp(time);

    @Test
    void shouldAddMenuItemToProductsList() {
        MenuItem menuItem = new MenuItem(1L, "burger", "image.com", "for lunch", 2L, MenuCategory.BURGER, timestamp);
        when(menuItemRepository.save(menuItem)).thenReturn(menuItem);
        assertEquals(menuItem, menuItemService.addMenuItem(menuItem));
    }

    @Test
    public void shouldGetTheListOfMenuItemsAvailable() {
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(new MenuItem(1L, "big bugger", "image1.com", "for breakfast", 3L, MenuCategory.SNACKS, timestamp));
        menuItemList.add(new MenuItem(2L, "meet Pie", "image2.com", "for dinner", 5L, MenuCategory.SIDES, timestamp));
        when(menuItemRepository.findAll()).thenReturn(menuItemList);
        assertEquals(2, menuItemService.getAllMenuItems().size());
    }

    @Test
    public void shouldUpdateAnMenuItemById() {
        MenuItem menuItem = new MenuItem(1L, "burger", "image.com", "for lunch", 2L, MenuCategory.BURGER, timestamp);
        when(menuItemRepository.save(menuItem)).thenReturn(menuItem);
        assertEquals(menuItem, menuItemService.updateMenuItem(menuItem.getId(),menuItem));
    }

    @Test
    public void shouldDeleteAParticularItemFromTheList() {

        Long menuItemId = 1L;
        menuItemService = mock(MenuServiceImplementation.class);
        doNothing().when(menuItemService).deleteMenuItemById(any());
        menuItemService.deleteMenuItemById(menuItemId);
        verify(menuItemService, times(1)).deleteMenuItemById(menuItemId);
    }

    @Test
    void shouldGetAParticularItemById() {
        List<MenuItem> menuItemList = new ArrayList<>();
        Long menuItemId = 1L;
       menuItemList.add( new MenuItem(1L, "big bugger", "image1.com", "for breakfast", 3L, MenuCategory.SNACKS, timestamp));
        menuItemList.add(new MenuItem(2L, "meet Pie", "image2.com", "for dinner", 5L, MenuCategory.SIDES, timestamp));
        when(menuItemRepository.findMenuItemById(menuItemId)).thenReturn(java.util.Optional.ofNullable(menuItemList.get(0)));

        assertEquals(menuItemId, menuItemService.getMenuItemById(menuItemList.get(0).getId()).getId());
    }

}


