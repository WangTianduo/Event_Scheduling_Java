from ics import Calendar, Event
import json
import time
import datetime

weekday = {'Mon':0, 'Tue':1, 'Wed':2, 'Thu':3, 'Fri':4, 'Sat':5, 'Sun':6}
day_slot = {0: '08:30:00', 1: '09:00:00',
			2: '09:30:00', 3: '10:00:00',
			4: '10:30:00', 5: '11:00:00',
			6: '11:30:00', 7: '12:00:00',
			8: '12:30:00', 9: '13:00:00',
			10: '13:30:00', 11: '14:00:00',
			12: '14:30:00', 13: '15:00:00',
			14: '15:30:00', 15: '16:00:00',
			16: '16:30:00', 17: '17:00:00',
			18: '17:30:00', 19: '18:00:00',
			20: '18:30:00', 21: '19:00:00'}

c = Calendar()

def genrate_ics(sClassSet, role, id, calendar): # role = {studentG, prof}
	for i in sClassSet:
		if id in i[role]:
			e = Event()
			now = datetime.datetime.now()

			name = str(i['subject']) +' ' + i['classroom']
			year = time.strftime("%Y", time.localtime())
			month = time.strftime("%m", time.localtime())
			today = time.strftime("%d", time.localtime())
			today_weekday = weekday[time.strftime("%a", time.localtime())]
			classday_whole = now + datetime.timedelta(((-today_weekday+i['weekday'])))
			classday = classday_whole.strftime('%d')
			start_time = day_slot[i['startTime']]
				
			ics_start = year+month+classday+'T'+ start_time + '+08:00'
			duration = i['duration']
			e.name = name
			e.begin = ics_start
			e.duration = {'days':0, 'hours':duration}
			calendar.events.add(e)

c0 = Calendar()			
c1 = Calendar()			
c2 = Calendar()			
c3 = Calendar()			
c4 = Calendar()			
c5 = Calendar()
c6 = Calendar()
c7 = Calendar()		
	
with open("timetable.json",'r') as load_f:
	load_dict = json.load(load_f)
	
	sClassSet = load_dict['specific class']
	genrate_ics(sClassSet, 'professor', 0, c0)
	genrate_ics(sClassSet, 'professor', 1, c1)
	genrate_ics(sClassSet, 'professor', 2, c2)
	genrate_ics(sClassSet, 'professor', 3, c3)
	genrate_ics(sClassSet, 'professor', 4, c4)
	genrate_ics(sClassSet, 'professor', 5, c5)
	genrate_ics(sClassSet, 'professor', 6, c6)
	genrate_ics(sClassSet, 'professor', 7, c7)
	
with open('my0.ics', 'w') as f:
	f.writelines(c0)		
with open('my1.ics', 'w') as f:
	f.writelines(c1)	
with open('my2.ics', 'w') as f:
	f.writelines(c2)	
with open('my3.ics', 'w') as f:
	f.writelines(c3)	
with open('my4.ics', 'w') as f:
	f.writelines(c4)	
with open('my5.ics', 'w') as f:
	f.writelines(c5)	
with open('my6.ics', 'w') as f:
	f.writelines(c6)	
with open('my7.ics', 'w') as f:
	f.writelines(c7)	
	
