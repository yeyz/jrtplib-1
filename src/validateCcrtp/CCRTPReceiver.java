/**
 * Java RTP Library (jlibrtp)
 * Copyright (C) 2006 Arne Kepp
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package validateCcrtp;

import java.net.DatagramSocket;
import jlibrtp.*;

/**
 * Receives and prints packets sent by the rtpsend demo program in ccrtp 1.5.x
 * 
 * Send them to port 6003, unless you modify this program.
 * 
 * @author Arne Kepp
 *
 */

public class CCRTPReceiver implements RTPAppIntf {
	RTPSession rtpSession = null;
	
	public CCRTPReceiver() {
		// Do nothing;
	}
	
	public void receiveData(RtpPkt frame, Participant p) {
		System.out.println("Got data: " + new String(frame.getPayload()));
	}
	
	public void userEvent(int type, Participant[] participant) {
		//Do nothing
	}
	
	public int getBufferSize() {
		return 1;
	}
	@Override
	public int getFirstSeqNumber() {
		return 0;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CCRTPReceiver me = new CCRTPReceiver();
		
		DatagramSocket rtpSocket = null;
		DatagramSocket rtcpSocket = null;
		
		try {
			rtpSocket = new DatagramSocket(16384);
			rtcpSocket = new DatagramSocket(16385);
		} catch (Exception e) {
			System.out.println("RTPSession failed to obtain port");
		}
		
		me.rtpSession = new RTPSession(rtpSocket, rtcpSocket);
		me.rtpSession.naivePktReception(true);
		me.rtpSession.RTPSessionRegister(me,null,null);
		
		Participant p = new Participant("127.0.0.1",16386,16387);		
		me.rtpSession.addParticipant(p);
	}

}
