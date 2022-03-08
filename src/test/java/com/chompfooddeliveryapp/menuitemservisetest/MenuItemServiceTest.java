//package com.chompfooddeliveryapp.menuitemservisetest;
//
//
//import com.chompfooddeliveryapp.dto.MenuItemDto;
//import com.chompfooddeliveryapp.model.enums.MenuCategory;
//import com.chompfooddeliveryapp.model.meals.MenuItem;
//import com.chompfooddeliveryapp.repository.MenuItemRepository;
//import com.chompfooddeliveryapp.service.serviceImpl.MenuServiceImplementation;
////import com.chompfooddeliveryapp.service.serviceInterfaces.MenuItemService;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//
//public class MenuItemServiceTest {
//
//
//
//
//
//    @Mock
//    MenuItemRepository menuItemRepository;
//
//    @InjectMocks
//    MenuServiceImplementation menuItemService;
//
//
//
//
//    Date date = new Date();
//    Long time = date.getTime();
//    Timestamp timestamp = new Timestamp(time);
//
//    @Test
//    void shouldAddMenuItemToProductsList() throws IOException {
//        MenuItemDto menuItemDto = new MenuItemDto("burger", 3.0, "nice burger",MenuCategory.BURGER,"images");
//        MenuItem menuItem = new MenuItem();
//        menuItem.setName(menuItemDto.getName());
//        menuItem.setPrice(menuItemDto.getPrice());
//        menuItem.setDescription(menuItemDto.getDescription());
//        menuItem.setCategory(menuItemDto.getCategory());
//        menuItem.setImage(menuItemDto.getImage());
//        when(menuItemRepository.save(menuItem)).thenReturn(menuItem);
//        assertEquals(menuItem, menuItemService.addMenuItem(menuItemDto));
//    }
//
//    @Test
//    public void shouldGetTheListOfMenuItemsAvailable() {
//        List<MenuItem> menuItemList = new ArrayList<>();
//        menuItemList.add(new MenuItem("big bugger", "image1.com", "for breakfast", 3.0, MenuCategory.SNACKS));
//        menuItemList.add(new MenuItem("meet Pie", "image2.com", "for dinner", 5.0, MenuCategory.SIDES));
//        when(menuItemRepository.findAll()).thenReturn(menuItemList);
//        assertEquals(2, menuItemService.getAllMenuItems().size());
//    }
//
//    @Test
//    public void shouldUpdateAnMenuItemById() throws IOException {
//        MenuItemDto menuItemDto = new MenuItemDto( "burger", 2.0, "for lunch", MenuCategory.BURGER,"Burger");
//        MenuItem menuItem = new MenuItem();
//        menuItem.setName(menuItemDto.getName());
//        menuItem.setPrice(menuItemDto.getPrice());
//        menuItem.setDescription(menuItemDto.getDescription());
//        menuItem.setCategory(menuItemDto.getCategory());
//        menuItem.setImage(menuItemDto.getImage());
//
//        when(menuItemRepository.save(menuItem)).thenReturn(menuItem);
//        assertEquals(menuItem, menuItemService.updateMenuItem(menuItem.getId(),menuItemDto));
//    }
//
//    @Test
//    public void shouldDeleteAParticularItemFromTheList() {
//
//        Long menuItemId = 1L;
//        menuItemService = mock(MenuServiceImplementation.class);
//        doNothing().when(menuItemService).deleteMenuItemById(any());
//        menuItemService.deleteMenuItemById(menuItemId);
//        verify(menuItemService, times(1)).deleteMenuItemById(menuItemId);
//    }
//
//    @Test
//    void shouldGetAParticularItemById() {
//        List<MenuItem> menuItemList = new ArrayList<>();
//        Long menuItemId = 1L;
//       menuItemList.add( new MenuItem("big bugger", "image1.com", "for breakfast", 3.0, MenuCategory.SNACKS));
//        menuItemList.add(new MenuItem("meet Pie", "image2.com", "for dinner", 5.0, MenuCategory.SIDES));
//        when(menuItemRepository.findMenuItemById(menuItemId)).thenReturn(java.util.Optional.ofNullable(menuItemList.get(0)));
//
//        assertEquals(menuItemId, menuItemService.getMenuItemById(menuItemList.get(0).getId()).getId());
//    }
//
//}
//
//
