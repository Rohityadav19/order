package com.ecommerce.order;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ecommerce.order.model.PaymentMethod;
import com.ecommerce.order.model.RequestOrder;
import com.ecommerce.order.model.RequestOrderCancellation;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc

class OrderApplicationTests {

	 @Autowired
	 private MockMvc mock;

	 private static final String token = ""
	 		+ "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aXJ1bWFsYXNldHR5dms5OEBnbWFpbC5jb20iLCJleHAiOjE2MzczMT"
	 		+ "k3ODQsImlhdCI6MTYzNzA2MDU4NH0.bTQjDVkI5ufNIlfarwZTCCuUou2CErWLuhqlBHYmPSE";
	 @Test
	 void placeOrder() throws Exception {

	 RequestOrder requestOrder=new RequestOrder();
	 
	 
	 	requestOrder.setPaymentMethod(PaymentMethod.WALLET);
	 	requestOrder.setDeliveryAddress("Guntur Narasaraopeta");
	 
	 ObjectMapper mapper = new ObjectMapper();
	 
	 String jsonData = mapper.writeValueAsString(requestOrder);
	 mock.perform(post("/orders/").content(jsonData).contentType("application/json").header("Authorization", "Bearer " + token))
	 .andExpect(status().isAccepted())
	 .andExpect(jsonPath("$").exists());
	 
	 // get all orders to display to user
	 mock.perform(get("/orders/").header("Authorization", "Bearer " + token))
	 .andExpect(status().isAccepted())
	 .andExpect(jsonPath("$").exists());
	 
	 mock.perform(get("/orders/isWalletAmountSufficient").header("Authorization", "Bearer " + token))
	 .andExpect(status().isAccepted())
	 .andExpect(jsonPath("$").exists());


	 }
	 
	 
	 @Test
	 void cancelOrder() throws Exception 
	 {
		 ObjectMapper mapper = new ObjectMapper();
	
		 
		 RequestOrderCancellation  cancel=new RequestOrderCancellation();
		 
		 cancel.setOrderId("OID12111163");
		 cancel.setQuantity(1);
		 cancel.setReason("Money is not available testing");
		 
		 String jsonDataTran = mapper.writeValueAsString(cancel);
		 mock.perform(put("/orders/").content(jsonDataTran).contentType("application/json").header("Authorization", "Bearer " + token))
		 .andExpect(status().isAccepted())
		 .andExpect(jsonPath("$").exists()); 
		 
//		  @GetMapping("/transactions")
//			ResponseEntity<List<Transactions>> getUserTransactions(String token); 
			 
			 mock.perform(get("/orders/transactions").header("Authorization", "Bearer " + token))
			 .andExpect(status().isAccepted())
			 .andExpect(jsonPath("$").exists()); 
			 
			 
			 mock.perform(get("/orders/createPdf/OID12111144").header("Authorization", "Bearer " + token))
			 .andExpect(status().isOk());
			
			 
			 
			 mock.perform(get("/orders/viewPdf/OID12111144").header("Authorization", "Bearer " + token))
			 .andExpect(status().isOk()); 
			 

	 }
	 
	

	 @Test
	 void NTests() throws Exception
	 {

	 RequestOrder requestOrder=new RequestOrder();
	 
	// requestOrder.set
	 
	 requestOrder.setPaymentMethod(PaymentMethod.WALLET);
	 requestOrder.setDeliveryAddress("Guntur Narasaraopeta");
	 ObjectMapper mapper = new ObjectMapper();
	 String jsonData = mapper.writeValueAsString(requestOrder);

	 mock.perform(post("/orders/").content(jsonData).contentType("application/json").header("Authorization",token))
	 .andExpect( status().isForbidden() ) ;

	 mock.perform(post("/orders/").header("Authorization","Bearer "+token))
	 .andExpect( status().isBadRequest() ) ;

	 mock.perform(get("/orders/").header("Authorization", token))
	 .andExpect( status().isForbidden() );

	 mock.perform(get("/orders/isWalletAmountSufficient").header("Authorization", token))
	 .andExpect(status().isForbidden() );


	  RequestOrder requestOrder1=new RequestOrder();
//	 requestOrder1.setPaymentMethod("");
	 requestOrder1.setDeliveryAddress("");
	 ObjectMapper mapper1 = new ObjectMapper();
	 String jsonData1= mapper1.writeValueAsString(requestOrder1);

	 mock.perform(post("/orders/").content(jsonData1).contentType("application/json").header("Authorization","Bearer "+token))
	 .andExpect( status().isBadRequest() ) ;

	 //-----------------post is not supported when get method is used

	 mock.perform(post("/orders/isWalletAmountSufficient").header("Authorization", "Bearer " + token))
	 .andExpect(status().isMethodNotAllowed() );


	 }


	 }


