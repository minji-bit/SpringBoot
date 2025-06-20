package web.mvc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    @Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long rno;//댓글번호
	
	private String content;//댓글내용
	
	@CreationTimestamp
	private LocalDateTime insertDate;
	
	@ManyToOne(fetch = FetchType.LAZY) //지연로딩!!
	@JoinColumn(name = "free_bno")
	private FreeBoard freeBoard;
	
	
	
}




