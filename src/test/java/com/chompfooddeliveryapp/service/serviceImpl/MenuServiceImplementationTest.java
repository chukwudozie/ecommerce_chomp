package com.chompfooddeliveryapp.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.chompfooddeliveryapp.exception.MenuNotFoundException;
import com.chompfooddeliveryapp.model.enums.MenuCategory;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.repository.MenuItemRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MenuServiceImplementation.class})
@ExtendWith(SpringExtension.class)
class MenuServiceImplementationTest {
    @MockBean
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuServiceImplementation menuServiceImplementation;

    @Test
    void testAddMenuItem() {
        MenuItem menuItem = new MenuItem();
        menuItem.setCategory(MenuCategory.BURGER);
        menuItem.setDateCreated(mock(Timestamp.class));
        menuItem.setId(123L);
        menuItem.setImage("Image");
        menuItem.setName("Name");
        menuItem.setPrice(1L);
        when(this.menuItemRepository.save((MenuItem) any())).thenReturn(menuItem);

        MenuItem menuItem1 = new MenuItem();
        menuItem1.setCategory(MenuCategory.BURGER);
        menuItem1.setDateCreated(mock(Timestamp.class));
        menuItem1.setId(123L);
        menuItem1.setImage("Image");
        menuItem1.setName("Name");
        menuItem1.setPrice(1L);
        assertSame(menuItem, this.menuServiceImplementation.addMenuItem(menuItem1));
        verify(this.menuItemRepository).save((MenuItem) any());
        assertEquals(
                "MenuItem(id=123, name=Name, image=Image, price=1, category=BURGER, dateCreated=1970-01-01" + " 01:00:00.0)",
                menuItem1.toString());
        assertTrue(this.menuServiceImplementation.getAllMenuItems().isEmpty());
    }

    @Test
    void testAddMenuItem2() {
        when(this.menuItemRepository.save((MenuItem) any())).thenThrow(new MenuNotFoundException("An error occurred"));

        MenuItem menuItem = new MenuItem();
        menuItem.setCategory(MenuCategory.BURGER);
        menuItem.setDateCreated(mock(Timestamp.class));
        menuItem.setId(123L);
        menuItem.setImage("Image");
        menuItem.setName("Name");
        menuItem.setPrice(1L);
        assertThrows(MenuNotFoundException.class, () -> this.menuServiceImplementation.addMenuItem(menuItem));
        verify(this.menuItemRepository).save((MenuItem) any());
    }

    @Test
    void testUpdateMenuItem() {
        MenuItem menuItem = new MenuItem();
        menuItem.setCategory(MenuCategory.BURGER);
        menuItem.setDateCreated(mock(Timestamp.class));
        menuItem.setId(123L);
        menuItem.setImage("Image");
        menuItem.setName("Name");
        menuItem.setPrice(1L);
        when(this.menuItemRepository.save((MenuItem) any())).thenReturn(menuItem);

        MenuItem menuItem1 = new MenuItem();
        menuItem1.setCategory(MenuCategory.BURGER);
        menuItem1.setDateCreated(mock(Timestamp.class));
        menuItem1.setId(123L);
        menuItem1.setImage("Image");
        menuItem1.setName("Name");
        menuItem1.setPrice(1L);
        assertSame(menuItem, this.menuServiceImplementation.updateMenuItem(menuItem1));
        verify(this.menuItemRepository).save((MenuItem) any());
        assertEquals(
                "MenuItem(id=123, name=Name, image=Image, price=1, category=BURGER, dateCreated=1970-01-01" + " 01:00:00.0)",
                menuItem1.toString());
        assertTrue(this.menuServiceImplementation.getAllMenuItems().isEmpty());
    }

    @Test
    void testUpdateMenuItem2() {
        when(this.menuItemRepository.save((MenuItem) any())).thenThrow(new MenuNotFoundException("An error occurred"));

        MenuItem menuItem = new MenuItem();
        menuItem.setCategory(MenuCategory.BURGER);
        menuItem.setDateCreated(mock(Timestamp.class));
        menuItem.setId(123L);
        menuItem.setImage("Image");
        menuItem.setName("Name");
        menuItem.setPrice(1L);
        assertThrows(MenuNotFoundException.class, () -> this.menuServiceImplementation.updateMenuItem(menuItem));
        verify(this.menuItemRepository).save((MenuItem) any());
    }

    @Test
    void testGetMenuItemById() {
        MenuItem menuItem = new MenuItem();
        menuItem.setCategory(MenuCategory.BURGER);
        menuItem.setDateCreated(mock(Timestamp.class));
        menuItem.setId(123L);
        menuItem.setImage("Image");
        menuItem.setName("Name");
        menuItem.setPrice(1L);
        Optional<MenuItem> ofResult = Optional.of(menuItem);
        when(this.menuItemRepository.findMenuItemById((Long) any())).thenReturn(ofResult);
        assertSame(menuItem, this.menuServiceImplementation.getMenuItemById(123L));
        verify(this.menuItemRepository).findMenuItemById((Long) any());
        assertTrue(this.menuServiceImplementation.getAllMenuItems().isEmpty());
    }

    @Test
    void testGetMenuItemById2() {
        when(this.menuItemRepository.findMenuItemById((Long) any()))
                .thenThrow(new MenuNotFoundException("An error occurred"));
        assertThrows(MenuNotFoundException.class, () -> this.menuServiceImplementation.getMenuItemById(123L));
        verify(this.menuItemRepository).findMenuItemById((Long) any());
    }

    @Test
    void testGetMenuItemById3() {
        when(this.menuItemRepository.findMenuItemById((Long) any())).thenReturn(Optional.empty());
        assertThrows(MenuNotFoundException.class, () -> this.menuServiceImplementation.getMenuItemById(123L));
        verify(this.menuItemRepository).findMenuItemById((Long) any());
    }

    @Test
    void testGetAllMenuItems() {
        ArrayList<MenuItem> menuItemList = new ArrayList<>();
        when(this.menuItemRepository.findAll()).thenReturn(menuItemList);
        List<MenuItem> actualAllMenuItems = this.menuServiceImplementation.getAllMenuItems();
        assertSame(menuItemList, actualAllMenuItems);
        assertTrue(actualAllMenuItems.isEmpty());
        verify(this.menuItemRepository).findAll();
    }

    @Test
    void testGetAllMenuItems2() {
        when(this.menuItemRepository.findAll()).thenThrow(new MenuNotFoundException("An error occurred"));
        assertThrows(MenuNotFoundException.class, () -> this.menuServiceImplementation.getAllMenuItems());
        verify(this.menuItemRepository).findAll();
    }

    @Test
    void testDeleteMenuItemById() {
        doNothing().when(this.menuItemRepository).deleteMenuItemById((Long) any());
        this.menuServiceImplementation.deleteMenuItemById(123L);
        verify(this.menuItemRepository).deleteMenuItemById((Long) any());
        assertTrue(this.menuServiceImplementation.getAllMenuItems().isEmpty());
    }

    @Test
    void testDeleteMenuItemById2() {
        doThrow(new MenuNotFoundException("An error occurred")).when(this.menuItemRepository)
                .deleteMenuItemById((Long) any());
        assertThrows(MenuNotFoundException.class, () -> this.menuServiceImplementation.deleteMenuItemById(123L));
        verify(this.menuItemRepository).deleteMenuItemById((Long) any());
    }
}

