package com.emadclinic.dentist.utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.emadclinic.dentist.pojo.Result;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenUtility {
	
	public final Integer expDate=60000;
	public final String keySign="Test";
	
	public String generateToken(String username) {
		Map<String,Object> test= new HashMap<>();
		test.put("username", username);
		
		//Date convertDatetime= new  Date(System.currentTimeMillis()+expDate);
		
		return Jwts.builder().setClaims(test)
				//.setExpiration(convertDatetime)
				.signWith(SignatureAlgorithm.HS512, keySign).compact();
	}
public Result checkToken(String token) {
		
	Result result= new Result();
	Map<String, Object> resultMap= new HashMap<>();
		try {
			Claims s= Jwts.parser().setSigningKey(keySign).parseClaimsJws(token).getBody();
			result.setStatusCode(0);
			result.setStatusDescription("success");
			String username =(String) s.get("username");
			resultMap.put("username", username);
			result.setResult(resultMap);
			return result;
			
			
			
		}catch (SignatureException ex) {
			result.setStatusCode(1);
			result.setStatusDescription("Failed");
			resultMap.put("error", "Invalid JWT signature");
			result.setResult(resultMap);
			return result;
			}catch (MalformedJwtException ex) {
				result.setStatusCode(1);
				result.setStatusDescription("Failed");
				resultMap.put("error", "Invalid JWT token");
				result.setResult(resultMap);
				return result;
			}catch (ExpiredJwtException ex) {
				result.setStatusCode(1);
				result.setStatusDescription("Failed");
				resultMap.put("error", "Expired JWT token");
				result.setResult(resultMap);
				return result;
			}catch (UnsupportedJwtException ex) {
				result.setStatusCode(1);
				result.setStatusDescription("Failed");
				resultMap.put("error", "Unsupported JWT token");
				result.setResult(resultMap);
				return result;
			}catch (IllegalArgumentException ex) {
				result.setStatusCode(1);
				result.setStatusDescription("Failed");
				resultMap.put("error", "JWT string is empty");
				result.setResult(resultMap);
				return result;
			}catch(Exception ex) {
				result.setStatusCode(1);
				result.setStatusDescription("Failed");
				resultMap.put("error", ex.getMessage());
				result.setResult(resultMap);
				return result;
			}
	}
	
}
