package com.filmservice.credential;

public class TestCredentialImpl implements CredentialDetail{

	@Override
	public String getPassword() {
		return "XXXX";
	}

	@Override
	public String getUserName() {
		return "XXXX";
	}

	@Override
	public String getServer() {
		return "localhost";
	} 

	@Override
	public int getPort() {
		return 8888;
	}


}
