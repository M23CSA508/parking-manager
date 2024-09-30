package org.mtech.csa.parking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mtech.csa.parking.entity.ParkingSpot;
import org.mtech.csa.parking.service.ParkingSpotService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ParkingSpotControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ParkingSpotService parkingSpotService;

    @InjectMocks
    private ParkingSpotController parkingSpotController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(parkingSpotController).build();
    }

    // Test case for getting available spots
    @Test
    void testGetAvailableSpots() throws Exception {
        List<ParkingSpot> expectedSpots = Collections.singletonList(new ParkingSpot());
        when(parkingSpotService.findAvailableSpots()).thenReturn(expectedSpots);

        mockMvc.perform(get("/v1/spots/available"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

        verify(parkingSpotService, times(1)).findAvailableSpots();
    }

    // Test case for creating a new parking spot
    @Test
    void testCreateParkingSpot() throws Exception {
        ParkingSpot newSpot = new ParkingSpot();
        newSpot.setLocation("Main Lot");
        newSpot.setLevel("Ground Floor");
        newSpot.setSpotNumber("A1");
        newSpot.setFee(new BigDecimal("10"));
        newSpot.setFeeUnit("hour");
        newSpot.setIsOccupied(false);

        when(parkingSpotService.createParkingSpot(any(ParkingSpot.class))).thenReturn(newSpot);

        mockMvc.perform(post("/v1/spots")
                        .content(asJsonString(newSpot))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.location", is("Main Lot")))
                .andExpect(jsonPath("$.level", is("Ground Floor")))
                .andExpect(jsonPath("$.spotNumber", is("A1")))
                .andExpect(jsonPath("$.fee", is(10)))
                .andExpect(jsonPath("$.feeUnit", is("hour")))
                .andExpect(jsonPath("$.isOccupied", is(false)));

        verify(parkingSpotService, times(1)).createParkingSpot(any(ParkingSpot.class));
    }

    // Utility method to convert object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
