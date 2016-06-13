package com.tochiku85.liketochiku.photorelay;

import java.util.ArrayList;
import java.util.Date;

public class InstagramPhoto {
	public String username;
	public String caption;
	public String imageUrl;
	public int imageHeight;
	public int imageWidth;
	public int likesCount;
//	public long createdTime;
	public Date createdTime;
	public String profilePictureUrl;
	public ArrayList<InstagramComment> comments;
}
