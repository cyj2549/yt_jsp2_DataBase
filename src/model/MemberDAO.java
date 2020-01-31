package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
// import java.sql.*; 이거쓰면 위에꺼 java.sql 안써도됨 
import java.util.Vector;

// 큐브리드 데이터 베이스에 연결하고 SELECT,INSERT, UPDATE,DELETE 작업을 실행해주는 클래스이다
public class MemberDAO {
	
	//큐브리드에 접속하는 소스를 작성
	String id = "dba";   //접속아이디
	String pass = "rhfi456";
	String url = "jdbc:cubrid:localhost:33000:CYJPRO:::?charset=UTF-8";
	
	Connection con; // 데이터베이스에 접근할 수 있도록 설정
	PreparedStatement pstmt; //데이터베이스에서 쿼리를 실행시켜주는 객체
	ResultSet rs; //데이터베이스의 테이블의 결과를 리턴받아 자바에 저장해 주는 객체
	
	
	
	//데이터 베이스에 접근할 수 있도록 도와주는 메소드
	public void getCon() {
		try {
			//1.해당 데이터 베이스를 사용한다고 선언(클래스를 등록=큐브리드용을 사용)
			Class.forName("core.log.jdbc.driver.CUBRIDDriver");
			
			//2.해당 데이터 베이스에 접속
			con = DriverManager.getConnection(url,id,pass);	
		}catch(Exception e){
			
		}
		
	}
	
	//데이터베이스에 한 사람의 회원 정보를 저장해주는 메소드
	public void insertMember(MemberBean mbean) {
		
		try{
			getCon();
			
			//3. 접속 후 쿼리준비
			String sql = "insert into member values (?,?,?,?,?,?,?,?)";
			
			// 쿼리를 사용하도록 설정	
			PreparedStatement pstmt= con.prepareStatement(sql); //jsp에서 쿼리를 사용하도록 설정
			
			// ?에 맞게 데이터를 맵핑
			pstmt.setString(1, mbean.getId());		
			pstmt.setString(2, mbean.getPass1());
			pstmt.setString(3, mbean.getEmail());
			pstmt.setString(4, mbean.getTel());
			pstmt.setString(5, mbean.getHobby());
			pstmt.setString(6, mbean.getJob());
			pstmt.setString(7, mbean.getAge());
			pstmt.setString(8, mbean.getInfo());		
			
			//4. 큐브리드에서 쿼리를 실행하시오 라는 소스코드 삽입
			pstmt.executeUpdate(); //insert , update ,delete 시 사용하는 메소드
			
			//5. 자원반납
			con.close();
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	
	
	//모든 회원의 정보를 리턴해주는 메소드 호출
	public Vector<MemberBean> allSelectMember(){
		
		//가변길이로 데이터를 저장
		Vector<MemberBean> v = new Vector<>();
		
		//데이터베이스는 무조건 예외처리 해야된다.
		try {
			
			getCon();  //커넥션 연결
			
			String sql = "select * from member" ; //쿼리준비
			
			pstmt = con.prepareStatement(sql); //쿼리를 실행시켜주는 객체 선언
			rs =pstmt.executeQuery(); //쿼리를 실행 시킨 결과를 리턴해서 받아줌.(큐브리드 테이블의 검색된 결과를 자바 객체에 저장)
			//rs 는 select 할때 필요
			//반복문을 사용해서 rs에 저장된 데이터를 추출해야됨
			while(rs.next()) {
				MemberBean bean = new MemberBean(); //컬럼으로 나뉘어진 데이터를 빈클래스에 저장
				bean.setId(rs.getString(1));
				bean.setPass1(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setTel(rs.getString(4));
				bean.setHobby(rs.getString(5));
				bean.setJob(rs.getString(6));
				bean.setAge(rs.getString(7));
				bean.setInfo(rs.getString(8));
				
				//패키징된 memberbean클래스를 벡터에 저장
				v.add(bean); //0번지부터 순서대로 데이터가 저장
			}
			//자원반납
			con.close();
			
		}catch(Exception e){
			
		}
		return v; // 다 저장된 벡터를 리턴
	}
	
	
	//한사람에 대한 정보를 리턴하는 메소드 작성
	public MemberBean oneSelectMember(String id){
		
		//한 사람에 대한 정보만 리턴하기에 빈클래스 객체 생성
		MemberBean bean = new MemberBean();
		
		try {
			getCon(); //커넥션 연결
			
			String sql = "select * from member where id = ?"; //쿼리준비
			pstmt=con.prepareStatement(sql);			
			//?에 값을 맵핑
			pstmt.setString(1,id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bean.setId(rs.getString(1));
				bean.setPass1(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setTel(rs.getString(4));
				bean.setHobby(rs.getString(5));
				bean.setJob(rs.getString(6));
				bean.setAge(rs.getString(7));
				bean.setInfo(rs.getString(8));
				
			}
			con.close(); //자원반납
		}catch(Exception e){
			e.printStackTrace();
		}
		return bean ; //bean 클래스 리턴
	}
	
	//한 회원의 패스워드 값을 리턴하는 메소드 작성
	public String getPass(String id) {
		
		String pass=""; //스트링으로 리턴을 해야하기에 스트링변수 선언
		
		try {
			getCon();
			
			String sql = "select pass1 from member where id = ?";	//쿼리준비		
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,id);			//?에 값을 맵핑
			rs=pstmt.executeQuery(); // 쿼리실행
			
			if(rs.next()) {
				pass=rs.getString(1); //패스워드값이 저장된 컬럼인덱스가 1번이기 때문에
			}
			
			con.close(); //자원반납
		}catch(Exception e){
			e.printStackTrace();	
		}
		return pass;
	}
	
	
	//한 회원의 정보를 수정하는 메소드
	public void updateMember(MemberBean bean) {
		
		getCon();
		
		try {
			String sql = "update member set email=?, tel=? where id =?"; //쿼리준비
			pstmt = con.prepareStatement(sql); //쿼리실행 객체 선언
			
			//?에 값을 맵핑
			pstmt.setString(1, bean.getEmail()); 
			pstmt.setString(2, bean.getTel());
			pstmt.setString(3, bean.getId());
			
			pstmt.executeUpdate();
			
			con.close(); //자원반납
		}catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	//한 회원을 삭제하는 메소드 작성
	public void deleteMember(String id) {
		
		getCon();
		
		try {
			String sql = "delete from member where id = ? "; //쿼리준비
			
			pstmt = con.prepareStatement(sql);	// 쿼리실행 객체선언
			pstmt.setString(1,id); // ?에 값을 맵핑
			pstmt.executeUpdate(); // 쿼리 실행
			System.out.println("삭제삭제");	
			con.close(); // 자원반납

			
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	
}
