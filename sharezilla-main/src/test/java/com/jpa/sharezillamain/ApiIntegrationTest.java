package com.jpa.sharezillamain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.sharezillamain.dto.AuthResponse;
import com.jpa.sharezillamain.dto.LoginRequest;
import com.jpa.sharezillamain.dto.RegisterRequest;
import com.jpa.sharezillamain.model.RoleType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    static String jwtTokenUploader;
    static String jwtTokenAdmin;

    @Test
    @Order(1)
    public void registerUploader() throws Exception {
        RegisterRequest request = new RegisterRequest("uploader1", "password", RoleType.UPLOADER);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(2)
    public void registerAdmin() throws Exception {
        RegisterRequest request = new RegisterRequest("admin1", "password", RoleType.ADMIN);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(3)
    public void loginUploader() throws Exception {
        LoginRequest request = new LoginRequest("uploader1", "password");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        AuthResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), AuthResponse.class);
        jwtTokenUploader = response.getToken();
    }

    @Test
    @Order(4)
    public void loginAdmin() throws Exception {
        LoginRequest request = new LoginRequest("admin1", "password");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        AuthResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), AuthResponse.class);
        jwtTokenAdmin = response.getToken();
    }

    // --- FileController Tests ---
    @Test
    @Order(5)
    @WithMockUser(username = "uploader1", roles = {"UPLOADER"})
    public void uploadFileAsUploader() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/files/upload")
                        .file(new MockMultipartFile("file", "testfile.txt", "text/plain", "dummy content".getBytes()))
                        .header("Authorization", "Bearer " + jwtTokenUploader))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("File uploaded"));
    }

    @Test
    @Order(6)
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    public void uploadFileAsAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/files/upload")
                        .file(new MockMultipartFile("file", "testfile.txt", "text/plain", "dummy content".getBytes()))
                        .header("Authorization", "Bearer " + jwtTokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("File uploaded"));
    }

    @Test
    @Order(7)
    public void listFilesAsUploader() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/files/list")
                        .header("Authorization", "Bearer " + jwtTokenUploader))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(8)
    public void downloadFile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/files/download/{filename}", "testfile.txt")
                        .header("Authorization", "Bearer " + jwtTokenUploader))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(9)
    public void deleteFile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/files/delete/{filename}", "dummy.txt")
                        .header("Authorization", "Bearer " + jwtTokenUploader))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("File deleted"));
    }

    // --- PublicDataController Tests ---
    @Test
    @Order(12)
    public void getAllPublicData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/public-data"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(13)
    public void getPublicDataById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/public-data/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(14)
    public void downloadPublicData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/public-data/{id}/download", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(10)
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    public void createPublicDataAsAdmin() throws Exception {
        // Create fake file for testing
        MockMultipartFile file = new MockMultipartFile(
                "file",                          // name of the param in controller
                "testfile.txt",                  // original filename
                MediaType.TEXT_PLAIN_VALUE,
                "This is test file content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/public-data")
                        .file(file)
                        .param("title", "Test Data")
                        .param("content", "Test Content")
                        .header("Authorization", "Bearer " + jwtTokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Data"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Test Content"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.filePath").exists());
    }



    @Test
    @Order(11)
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    public void updatePublicDataAsAdmin() throws Exception {
        MockMultipartFile updatedFile = new MockMultipartFile(
                "file",
                "updatedfile.txt",
                "text/plain",
                "Updated file content".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/public-data/{id}", 1L)
                        .file(updatedFile)
                        .param("title", "Updated Data")
                        .param("content", "Updated Content")
                        .with(request -> {
                            request.setMethod("PUT"); // Hack for multipart PUT
                            return request;
                        })
                        .header("Authorization", "Bearer " + jwtTokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Data"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.filePath").exists());
    }


    @Test
    @Order(15)
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    public void deletePublicDataAsAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/public-data/{id}", 1L)
                        .header("Authorization", "Bearer " + jwtTokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(16)
    @WithMockUser(username = "uploader1", roles = {"UPLOADER"})
    public void accessAdminEndpointAsUploader() throws Exception {
        // Mock file for upload
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",                     // name of the file param
                "test.txt",                 // original filename
                "text/plain",               // content type
                "Test content".getBytes()   // file content
        );

        // Adding title, description, and content parameters
        mockMvc.perform(MockMvcRequestBuilders.multipart("/public-data")
                        .file(mockFile)
                        .param("title", "Sample Title")       // Add 'title' param
                        .param("description", "Sample Description")  // Add 'description' param
                        .param("content", "This is the content")  // Add 'content' param
                        .header("Authorization", "Bearer " + jwtTokenUploader)) // Include Authorization header
                .andExpect(MockMvcResultMatchers.status().isForbidden());  // Expected status code
    }



}
