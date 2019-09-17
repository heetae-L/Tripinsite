import pymysql
from matplotlib import pyplot
from print_df import print_df
from pandas import DataFrame

area_dic = {"서울" : 1, "인천": 2, "대전" : 3, "대구": 4, "광주" : 5, "부산" : 6, "울산" : 7, "세종특별자치시" : 8, "경기도" : 31, "강원도" : 32, "충청북도" : 33, "충청남도" : 34, "경상북도" : 35, "경상남도" : 36, "전라북도" : 37, "전라남도" : 38, "제주도" : 39}
area_dic = {v: k for k, v in area_dic.items()}

keys = list(area_dic.keys())
values = list(area_dic.values())

# MySQL Connection 연결
conn = pymysql.connect(host='localhost',user='root',password='root',db='tripinsite',charset='utf8')

# Connection 으로부터 Cursor 생성

curs = conn.cursor(pymysql.cursors.DictCursor)

# SQL문 실행
sql = 'SELECT COUNT(idsche_table) as value,a.areacode FROM sche_table t INNER JOIN api a ON t.api_contentid = a.contentid INNER JOIN schedule s ON t.schedule_idschedule = s.idschedule GROUP BY a.areacode'
curs.execute(sql)

# 데이터 Fatch
rows = curs.fetchall()
conn.close()

df = DataFrame(rows)
print_df(df)

df_area = list(df['areacode'])
for i,v in enumerate(df_area):
    if v in keys:
        df_area[i] = area_dic[v]

# 한글폰트 설정 및 그래프 크기 설정
pyplot.rcParams['font.family'] = 'NanumGothic'
pyplot.rcParams["font.size"] = 16
pyplot.rcParams['figure.figsize'] = (12,8)

# 표시할 데이터 설정
# -> 총 합이 100이 아닐 경우 라이브러리가 자동으로 비율을 계산함
ratio = list(df['value'])
# 각 항목의 라벨
labels =df_area
# 각 항목별 색상
colors = ['#00FF7F','#E9967A','#00FFFF','#DAA520','#E2CCE7','#4776B4','#DEDEDE','#FFF44F', '#BA55D3', '#FFA6C6','#85C298','#FA6B67','#FDD866','#9DE5F3', '#BFFF00', '#F7BD42', '#6798D3']

pyplot.figure()
pyplot.title('지역별 방문 비율')

# 파이차트 표시(데이터,라벨,색상,확대,수치값,표시형식,그림자,시작각도)
# -> autopct 파라미터 설정 안할 경우 수치값 표시 안됨
#            의미: %0.2f (소수점 2째 자리까지 표시) + %%(순수한 %기호)
# -> startangle 기본값은 0도
# -> 각 데이터 항목들은 반시계 방향으로 회존하면서 배치됨
pyplot.pie(ratio,labels=labels,colors=colors,autopct='%0.1f%%',shadow=False,startangle=90)
pyplot.savefig(r'C:\KJH\JSP\upload\area_avg.png',dpi=200)
pyplot.close()