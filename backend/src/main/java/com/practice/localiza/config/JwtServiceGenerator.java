package com.practice.localiza.config;

//JwtService.java

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import com.practice.localiza.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
@Service
public class JwtServiceGenerator {  

	///////////////////////////////////////////////////////
	//Parâmetros para geração do token
    @Value("${jwt.secret}")
    private String SECRET_KEY;
	public static final SignatureAlgorithm ALGORITMO_ASSINATURA = SignatureAlgorithm.HS256;
    @Value("${jwt.expiration-hours}")
	public static final int HORAS_EXPIRACAO_TOKEN = 1;

    public Map<String, Object> gerarPayload(User user){

        Map<String, Object> payloadData = new HashMap<>();
        payloadData.put("username", user.getUsername());
        payloadData.put("id", user.getId().toString());
        payloadData.put("role", user.getRole());
		//payloadData.put("outracoisa", "teste");
		
		return payloadData;
	}

	///////////////////////////////////////////////////////

	

	public String generateToken(User user) {

		Map<String, Object> payloadData = this.gerarPayload(user);

		return Jwts
				.builder()
				.setClaims(payloadData)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(new Date().getTime() + 3600000 * this.HORAS_EXPIRACAO_TOKEN))
				.signWith(getSigningKey(), this.ALGORITMO_ASSINATURA)
				.compact();
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}


	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}


	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

}
