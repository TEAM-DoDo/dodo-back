package kr.ac.hansung.dodobackend.controller;

import kr.ac.hansung.dodobackend.model.Do;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/do")
public class DoController {
    private Map<Integer, Do> doDummy = new HashMap<>();
    @GetMapping("/list")
    public ResponseEntity<Map<String,Object>> getDoIDList(){
        Map<String,Object> result = new HashMap<>();
        //현재는 받아온 키셋을 넘겨 보내도록 하고 있음 향후 쿼리로 변경하여 바로 리스트를 받아와 전송하도록 하면 됨
        List<Integer> doList = new ArrayList<>(doDummy.keySet());
        result.put("do_id",doList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<?> createDO(@Validated @RequestBody Do doInfo){
        //현재는 더미에 데이터를 저장하도록 되어있음
        doDummy.put(doDummy.size(),doInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{do_id}")
    public ResponseEntity<Do> getDo(@PathVariable("do_id") int doId){
        Do data = doDummy.get(doId);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
