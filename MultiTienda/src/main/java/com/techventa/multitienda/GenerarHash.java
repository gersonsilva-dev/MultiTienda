package com.techventa.multitienda;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarHash {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(new BCryptPasswordEncoder().encode("al"));
		
		
	}

}
