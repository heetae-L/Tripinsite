from print_df import print_df
import pymysql
from matplotlib import pyplot
from pandas import DataFrame

# MySQL Connection 연결
conn = pymysql.connect(host='localhost',user='root',password='root',db='tripinsite',charset='utf8')

# Connection 으로부터 Cursor 생성
curs = conn.cursor(pymysql.cursors.DictCursor)

# SQL문 실행
sql = 'SELECT COUNT(DATE_FORMAT(start_day, "%y-%m")) AS count, DATE_FORMAT(start_day, "%y-%m") AS month FROM schedule GROUP BY DATE_FORMAT(start_day, "%y-%m") ORDER BY DATE_FORMAT(start_day, "%Y-%m") ASC LIMIT 0 , 11;'
curs.execute(sql)

# 데이터 Fatch
rows = curs.fetchall()

conn.close()

data = DataFrame(rows)
print_df(data)


# 한글폰트 설정 및 그래프 크기 설정
pyplot.rcParams['font.family'] = 'NanumGothic'
pyplot.rcParams["font.size"] = 16
pyplot.rcParams['figure.figsize'] = (12,8)

pyplot.figure()
pyplot.title('월별 출발일정 수')
pyplot.plot(data['count'], label='여행출발 일정', linestyle='-', marker='.',color='#ff6600')
pyplot.xticks([0,1,2,3,4,5,6,7,8,9,10,11],data['month'])
pyplot.grid()
pyplot.xlabel('출발일정')
pyplot.ylabel('작성된 게시글')
pyplot.legend()
#pyplot.show()
pyplot.savefig(r'C:\KJH\JSP\upload\start_avg.png',dpi=200)
pyplot.close()