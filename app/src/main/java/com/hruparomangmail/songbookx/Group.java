package com.hruparomangmail.songbookx;

 public class Group {
    private String name;
    private String id;
     private String currentSong;
    private boolean songIsPublic;

    public Group(String name, String id,String currentSong, boolean status){
        this.name = name;
        this.id = id;
        this.currentSong = currentSong;
        this.songIsPublic = status;
    }
    public String getName(){
        return name;
    }

    public String getId() {
        return id;
    }
     public String getCurrentSong() {
         return currentSong;
     }

    public boolean isSongIsPublic() {
        return songIsPublic;
    }

}
