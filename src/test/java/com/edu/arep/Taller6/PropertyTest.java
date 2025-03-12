package com.edu.arep.Taller6;

import com.edu.arep.Taller6.Controller.PropertyController;
import com.edu.arep.Taller6.Entity.Property;
import com.edu.arep.Taller6.Service.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PropertyTest {

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private PropertyController propertyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProperties() {

        Property property1 = new Property("Calle 123", 150000.0, 120.5, "Casa amplia");
        Property property2 = new Property("Avenida 456", 200000.0, 150.0, "Casa moderna");
        when(propertyService.getAllProperties()).thenReturn(Arrays.asList(property1, property2));
        List<Property> properties = propertyController.getAllProperties();
        assertEquals(2, properties.size());
        verify(propertyService, times(1)).getAllProperties();
    }

    @Test
    void getPropertyById() {
        Property property = new Property("Calle 123", 150000.0, 120.5, "Casa amplia");
        when(propertyService.getPropertyById(1L)).thenReturn(Optional.of(property));
        ResponseEntity<Property> response = propertyController.getPropertyById(1L);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Calle 123", response.getBody().getAddress());
        verify(propertyService, times(1)).getPropertyById(1L);
    }

    @Test
    void createProperty() {
        Property property = new Property("Calle 123", 150000.0, 120.5, "Casa amplia");
        when(propertyService.createProperty(property)).thenReturn(property);
        Property savedProperty = propertyController.createProperty(property);
        assertNotNull(savedProperty);
        assertEquals("Calle 123", savedProperty.getAddress());
        verify(propertyService, times(1)).createProperty(property);
    }

    @Test
    void updateProperty() {
        Property updatedDetails = new Property("Avenida 456", 200000.0, 150.0, "Casa moderna");
        Property updatedProperty = new Property("Avenida 456", 200000.0, 150.0, "Casa moderna");
        when(propertyService.updateProperty(1L, updatedDetails)).thenReturn(updatedProperty);
        ResponseEntity<Property> response = propertyController.updateProperty(1L, updatedDetails);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Avenida 456", response.getBody().getAddress());
        verify(propertyService, times(1)).updateProperty(1L, updatedDetails);
    }

    @Test
    void deleteProperty() {
        doNothing().when(propertyService).deleteProperty(1L);
        ResponseEntity<Void> response = propertyController.deleteProperty(1L);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(propertyService, times(1)).deleteProperty(1L);
    }
}