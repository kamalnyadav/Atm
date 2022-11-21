package com.zinkworks.springboot.atm.bo.helper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.zinkworks.springboot.atm.entity.Atms;

@Component
public class AtmHelper {

	Map<Integer, Integer> cashNotes;
	
	Atms notePairs;
	
	public Map<Integer, Integer> getDenominationPairs(Atms denominations) {
		cashNotes = new HashMap<>();
		cashNotes.put(50, denominations.getFifty());
		cashNotes.put(20, denominations.getTwenty());
		cashNotes.put(10, denominations.getTen());
		cashNotes.put(5, denominations.getFive());
		return cashNotes;
	}
	
	public int calculateTotalAmt(Map<Integer, Integer> map) {
		int amt = 0;
		Set<Map.Entry<Integer, Integer>> entrySet = map.entrySet();
		Iterator<Map.Entry<Integer,Integer>> itr = entrySet.iterator();
		while (itr.hasNext()) {
			Map.Entry<Integer, Integer> entry = itr.next();
			amt = amt + entry.getKey() * entry.getValue();
		}
		return amt;
	}
	
	public Atms getNotePairs(int[] noteCounter) {
		notePairs = new Atms();
		notePairs.setFifty(noteCounter[0]);
		notePairs.setTwenty(noteCounter[1]);
		notePairs.setTen(noteCounter[2]);
		notePairs.setFive(noteCounter[3]);
		return notePairs;
	}
	
	public Atms getNotePairs(Map<Integer, Integer> map) {
		notePairs = new Atms();
		notePairs.setFifty(map.get(50));
		notePairs.setTwenty(map.get(20));
		notePairs.setTen(map.get(10));
		notePairs.setFive(map.get(5));
		return notePairs;
	}
	
	public int calculateTotalAmt(int[] noteCounter) {
		int amt = 0;
		amt+= noteCounter[0]*50;
		amt+= noteCounter[1]*20;
		amt+= noteCounter[2]*10;
		amt+= noteCounter[3]*5;
		return amt;
	}

}
