package com.heun.trip.web.json;
 
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.heun.trip.conf.Sms;
import com.heun.trip.domain.Member;
import com.heun.trip.domain.Rev;
import com.heun.trip.domain.Room;
import com.heun.trip.service.MemberService;
import com.heun.trip.service.PaymentsService;
import com.heun.trip.service.RevService;
import com.heun.trip.service.RoomService;


@RestController("json/RevController")
@RequestMapping("/json/rev")
public class RevController {

  RevService revService;
  MemberService memberService;
  RoomService roomService;
  PaymentsService paymentsService;
  Sms sms;

  public RevController(RevService revService, MemberService memberService, RoomService roomService, Sms sms, PaymentsService paymentsService) {
    this.revService = revService;
    this.memberService = memberService;
    this.roomService = roomService;
    this.sms = sms;
    this.paymentsService = paymentsService;
  }

  @PostMapping("update")
  public Object add(Rev rev, HttpSession session) {
    HashMap<String,Object> content = new HashMap<>();
    Member loginUser = (Member) session.getAttribute("loginUser");

    int userNo = loginUser.getNo();
    rev.setUserNo(userNo);

    Rev revs = revService.detail(rev.getRevUpdate());
    System.out.println(revs);
    rev.setStusNo(revs.getStusNo());
    rev.setRmsNo(revs.getRmsNo());
    rev.setRevStus(revs.getRevStus());
    rev.setRevCharge(revs.getRevCharge());

    try {
      revService.inupdate(rev);
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @GetMapping("updatesms")
  public Object addsms(int roomNo, HttpSession session) {

    Member loginUser = (Member) session.getAttribute("loginUser");
    String guestName = loginUser.getName();
    String tel = memberService.getTel(roomNo);
    String roomName = roomService.getRoom(roomNo);

    String messageText = roomName + " 숙소의 " + guestName + "님의 예약 변경 요청.";
    try {
      sms.smsSend(tel, messageText);
    } catch (Exception e) {
      e.printStackTrace();
    }

    HashMap<String,Object> content = new HashMap<>();

    return content;
  }


  // 예약 변경 완료시 게스트에게 날라 갈 문자
  @GetMapping("updateCompleteSms")
  public Object addCompleteSms(int no) {

    Rev rev = revService.detail(no);
    int rmsNo = rev.getRmsNo();
    int userNo = rev.getUserNo();

    Member member = memberService.get(userNo);

    String guestName = member.getName();
    String tel = member.getTel();
    String roomName = roomService.getRoom(rmsNo);

    String messageText = roomName + "의 " + guestName + "님의 예약 변경이 완료되었습니다.\n";

    try {
      sms.smsSend(tel, messageText);
    } catch (Exception e) {
      e.printStackTrace();
    }

    HashMap<String,Object> content = new HashMap<>();

    return content;
  }

  // 예약 변경 거절시 게스트에게 날라 갈 문자
  @GetMapping("updateCancelSms")
  public Object addCancelSms(int no) {

    Rev rev = revService.detail(no);
    int rmsNo = rev.getRmsNo();
    int userNo = rev.getUserNo();

    Member member = memberService.get(userNo);

    String guestName = member.getName();
    String tel = member.getTel();
    String roomName = roomService.getRoom(rmsNo);

    String messageText = roomName + "의 " + guestName + "님의 예약 변경이 거절되었습니다.\n";

    try {
      sms.smsSend(tel, messageText);
    } catch (Exception e) {
      e.printStackTrace();
    }

    HashMap<String,Object> content = new HashMap<>();

    return content;
  }

  @GetMapping("cancelsms")
  public Object cancelsms(int roomNo, HttpSession session) {

    Member loginUser = (Member) session.getAttribute("loginUser");
    String guestName = loginUser.getName();
    String tel = memberService.getTel(roomNo);
    String roomName = roomService.getRoom(roomNo);

    String messageText = roomName + " 숙소의 " + guestName + "님의 예약 취소 요청. ";
    try {
      sms.smsSend(tel, messageText);
    } catch (Exception e) {
      e.printStackTrace();
    }

    HashMap<String,Object> content = new HashMap<>();

    return content;
  }

  @GetMapping("list")
  public Object list(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="5") int pageSize, 
      HttpSession session
      ) { 
    Member member = (Member)session.getAttribute("loginUser");
    HashMap<String,Object> content = new HashMap<>();

    try {

      int userNo = member.getNo();

      if (pageSize < 1 || pageSize > 6) 
        pageSize = 5;

      int rowCount = revService.size(userNo);
      int totalPage = rowCount / pageSize;
      if (rowCount % pageSize > 0)
        totalPage++;

      if (pageNo < 1) 
        pageNo = 1;
      else if (pageNo > totalPage)
        pageNo = totalPage;

      List<Rev> reservation = revService.list(pageNo, pageSize, userNo);

      for(Rev r : reservation) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("userNo", r.getUserNo());
        params.put("roomNo", r.getRmsNo());

        int no = revService.count(params);

        System.out.println(no);

        if(no == 1) {
          r.setCount(true);
        }
      }

      content.put("status", "success");
      content.put("list", reservation);
      content.put("pageNo", pageNo);
      content.put("pageSize", pageSize);
      content.put("totalPage", totalPage);
    } catch (Exception e) {
      content.put("status", "fail");
    }

    return content;
  }


  @GetMapping("listInHostPage")
  public Object listInHostPage(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="5") int pageSize, 
      HttpSession session
      ) { 
    Member member = (Member)session.getAttribute("loginUser");
    HashMap<String,Object> content = new HashMap<>();
    try {
    int userNo = member.getNo();

    if (pageSize < 1 || pageSize > 6) 
      pageSize = 5;

    int rowCount = revService.countInHostPage(userNo);
    int totalPage = rowCount / pageSize;
    if (rowCount % pageSize > 0)
      totalPage++;

    if (pageNo < 1) 
      pageNo = 1;
    else if (pageNo > totalPage)
      pageNo = totalPage;

      List<Rev> reservation = revService.listInHostPage(pageNo, pageSize, userNo);

      content.put("list", reservation);
      content.put("pageNo", pageNo);
      content.put("pageSize", pageSize);
      content.put("totalPage", totalPage);
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
    }

    return content;
  }

  @PostMapping("deleteInHostpage")
  public Object deleteInHostPage(int no) {
    HashMap<String,Object> content = new HashMap<>();
    try {
      Rev rev = revService.detail(no);
      String impUid = rev.getImpUid();
      
      System.out.println(impUid);
      
      Map<String, Object> params = new HashMap<>();
      
      params.put("imp_uid", impUid);
      
      paymentsService.buyCancel(params);
      revService.deleteInHostpage(no);
      
      content.put("status", "success");
    } catch (Exception e) {
      content.put("fail", "삭제 실패!");
    }
    return content;
  }

  @GetMapping("detail")
  public Object detail(int no) {
    Rev rev = revService.detail(no);
    HashMap<String,Object> content = new HashMap<>();
    content.put("rev", rev);

    return content;
  }

  @GetMapping("listup")
  public Object listup(
      @RequestParam(defaultValue="1") int pageNo,
      @RequestParam(defaultValue="5") int pageSize, 
      HttpSession session
      ) {

    Member member = (Member)session.getAttribute("loginUser");
    HashMap<String,Object> content = new HashMap<>();

    try {

      int userNo = member.getNo();

      if (pageSize < 1 || pageSize > 6) 
        pageSize = 5;

      int rowCount = revService.size(userNo);
      int totalPage = rowCount / pageSize;
      if (rowCount % pageSize > 0)
        totalPage++;

      if (pageNo < 1) 
        pageNo = 1;
      else if (pageNo > totalPage)
        pageNo = totalPage;

      List<Rev> reservation = revService.getupdtData(pageNo, pageSize, userNo);

      System.out.println(reservation);

      for(Rev r : reservation) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("userNo", r.getUserNo());
        params.put("roomNo", r.getRmsNo());

        int no = revService.count(params);

        System.out.println(no);

        if(no == 1) {
          r.setCount(true);
        }
      }

      content.put("list", reservation);
      content.put("status", "success");
      content.put("pageNo", pageNo);
      content.put("pageSize", pageSize);
      content.put("totalPage", totalPage);
    } catch (Exception e) {
      content.put("status", "fail");
    }

    return content;
  }

  @PostMapping("cancel")
  public Object cancel(int no) {
    HashMap<String,Object> content = new HashMap<>();

    System.out.println(no);

    try {
      revService.cancel(no);
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  // 예약을 변경한다.
  @PostMapping("change")
  public Object change(int no) {
    HashMap<String,Object> content = new HashMap<>();

    try {
      revService.change(no);
      content.put("status", "success");
    } catch (Exception e) {
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }
    return content;
  }

  @SuppressWarnings("unchecked")
  @PostMapping("complete")
  public Object complete(String imp_uid, HttpSession session) {

    Map<String,Object> info = (Map<String,Object>) session.getAttribute("buyInfo");
    info.put("imp_uid", imp_uid);

    Map<String,Object> content = new HashMap<>();

    Rev rev = (Rev) info.get("rev");
    rev.setStusNo(1);
    rev.setImpUid(imp_uid);
    
    try {
      boolean isbuyCheck = paymentsService.buyCheck(info);
      boolean isAdd = false;
      if (isbuyCheck) {
        isAdd = revService.add(rev);
      } 
      if (isbuyCheck && isAdd) {
        content.put("status", "success");
      } else {
        paymentsService.buyCancel(info);
        throw new Exception();
      }
    } catch (Exception e) {
      e.printStackTrace();
      content.put("status", "fail");
      content.put("message", e.getMessage());
    }

    return content;
  }

  @PostMapping("getbuyinfo")
  public Object getBuyInfo(Rev rev, HttpSession session) {

    HashMap<String, Object> content = new HashMap<>();
    Room room = roomService.get(rev.getRmsNo());
    Member member = (Member)session.getAttribute("loginUser");

    int price = Integer.parseInt(room.getPrice());
    int sum = 0;

    try {

      if (member == null) {
        throw new Exception("로그인 해주세요.");
      }

      Date beginDate = rev.getCheckIn();
      Date endDate = rev.getCheckOut();

      // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
      long diff = endDate.getTime() - beginDate.getTime();
      long diffDays = diff / (24 * 60 * 60 * 1000);

      System.out.println("날짜차이=" + diffDays);

      sum = (int) ((price * (int) diffDays) * 1.1);

      String merchantUid = UUID.randomUUID().toString();

      rev.setMerchantUid(merchantUid);
      rev.setRevCharge(sum);
      rev.setUserNo(member.getNo());

      content.put("name", room.getName() + " " + diffDays + "박");
      content.put("amount", sum);
      content.put("merchant_uid", merchantUid);
      content.put("buyer_email", member.getEmail());
      content.put("buyer_name", member.getName());
      content.put("buyer_tel", member.getTel());
      content.put("rev", rev);

      session.setAttribute("buyInfo", content);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return content;
  }

}
