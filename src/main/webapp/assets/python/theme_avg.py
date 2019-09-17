import pymysql
from matplotlib import pyplot
from pandas import DataFrame
from print_df import print_df

theme_dic = {"식도락" : 1, "쇼핑": 2, "명소" : 3, "휴양": 4}
theme_dic = {v: k for k, v in theme_dic.items()}

keys = list(theme_dic.keys())
values = list(theme_dic.values())

# MySQL Connection 연결
conn = pymysql.connect(host='localhost',user='root',password='root',db='tripinsite',charset='utf8')

# Connection 으로부터 Cursor 생성
curs = conn.cursor(pymysql.cursors.DictCursor)

# SQL문 실행
sql = 'SELECT count(theme) as count,theme FROM schedule GROUP BY theme'
curs.execute(sql)

# 데이터 Fatch
rows = curs.fetchall()
conn.close()

df = DataFrame(rows)
print_df(df)

df_theme = list(df['theme'])
for i,v in enumerate(df_theme):
    if v in keys:
        df_theme[i] = theme_dic[v]

# 한글폰트 설정 및 그래프 크기 설정
pyplot.rcParams['font.family'] = 'NanumGothic'
pyplot.rcParams["font.size"] = 16
pyplot.rcParams['figure.figsize'] = (12,8)

# 표시할 데이터 설정
# -> 총 합이 100이 아닐 경우 라이브러리가 자동으로 비율을 계산함
ratio = list(df['count'])
# 각 항목의 라벨
labels = df_theme
# 각 항목별 색상
colors = ['#BFFF00','#FA6B67','#FDD866','#9DE5F3']

pyplot.figure()
pyplot.title('테마별 게시글 수')

# 파이차트 표시(데이터,라벨,색상,확대,수치값,표시형식,그림자,시작각도)
# -> autopct 파라미터 설정 안할 경우 수치값 표시 안됨
#            의미: %0.2f (소수점 2째 자리까지 표시) + %%(순수한 %기호)
# -> startangle 기본값은 0도
# -> 각 데이터 항목들은 반시계 방향으로 회존하면서 배치됨
pyplot.pie(ratio,labels=labels,colors=colors,autopct='%0.1f%%',shadow=False,startangle=90)

pyplot.savefig(r'C:\KJH\JSP\upload\theme_avg.png',dpi=200)
pyplot.close()
