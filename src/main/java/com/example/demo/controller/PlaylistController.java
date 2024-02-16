package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Playlist;
import com.example.demo.entities.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PlaylistController {

	@Autowired
	SongService service;

	@Autowired
	PlaylistService playlistservice;

	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		List<Song> songList = service.fetchAllSongs();
		model.addAttribute("song", songList);
		return "createPlaylist";
	}

	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		// update playlist table
		playlistservice.addPlaylist(playlist);

		// updating song table
		List<Song> listsongs = playlist.getSong();
		for (Song s : listsongs) {
			s.getPlaylist().add(playlist);
			service.updateSong(s);
		}
		return "adminHome";
	}

	@GetMapping("/viewPlaylists")
	public String viewPlaylists(Model model) {
		List<Playlist> allplaylists=playlistservice.fetchAllPlaylists();
		model.addAttribute("playlists", allplaylists);
		return "displayPlaylists";
	}
	
}
