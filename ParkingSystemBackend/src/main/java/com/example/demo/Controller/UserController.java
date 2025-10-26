package com.example.demo.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.User;

import com.example.demo.Repo.UserRepo;



@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserRepo repo;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody User user){
		if(repo.findByEmail(user.getEmail()).isPresent())
		{
			return ResponseEntity.badRequest().body("user alerady exists");
		}
		repo.save(user);
		return ResponseEntity.ok("Signup successful");
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user){
		Optional<User> found = repo.findByEmail(user.getEmail());
		if(found.isPresent() && found.get().getPassword().equals(user.getPassword()))
		{
			return ResponseEntity.ok("Login successful");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
	}
}
