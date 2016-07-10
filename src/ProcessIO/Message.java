package ProcessIO;

class Message{
	public static int id;
	public String content;
	public String getContent(){
		return this.content;
	}
	public void setContent(String content){
		this.content = content;
	}
	public int getId(){
		return this.id;
	}
	public void setId( int id){
		this.id = id;
	}
}
