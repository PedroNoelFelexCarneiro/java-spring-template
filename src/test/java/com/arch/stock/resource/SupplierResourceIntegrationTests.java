package com.arch.stock.resource;

import com.arch.stock.util.StockMySQLDbCleanerExtension;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({StockMySQLDbCleanerExtension.class, SpringExtension.class})
public class SupplierResourceIntegrationTests extends SpringIntegrationTests {

    private static final String SUPPLIER_RESOURCE = "/v1/suppliers";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldListAllProducts() throws Exception {
        mockMvc.perform(get(SUPPLIER_RESOURCE).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$.[*].name", Matchers.containsInAnyOrder("Padaria Uniao", "Tauste", "Confianca")));
    }

    @Test
    public void shouldReturnOneProduct() throws Exception {
        mockMvc.perform(get(SUPPLIER_RESOURCE + "/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Padaria Uniao"));
    }

    @Test
    public void shouldNotReturnOneProduct() throws Exception {
        mockMvc.perform(get(SUPPLIER_RESOURCE + "/{id}", 4)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }


}
