# -*- coding: utf-8 -*-

from prettytable import PrettyTable
import pandas

#------------------------------------------
# 데이터 프레임 출력 함수
#------------------------------------------
def print_df(df):
    # 변수의 타입을 확인한다.
    print(type(df))

    # 타입이 DataFrame인 경우 사용
    if isinstance(df, pandas.core.frame.DataFrame):
        print(df.shape)
        # 파라미터로 전달된 데이터 프레임의 컬럼 이름들에 대한 리스트를
        # 사용하여 PrettyTable 객체 생성
        table = PrettyTable([''] + list(df.columns))

        # 데이터프레임의 각 행마다 반복
        for row in df.itertuples():
            # 데이터 프레임의 각 행을 PrettyTable 객체에 추가
            table.add_row(row)

        # 결과를 문자열로 변환하여 출력
        print(str(table))

    # 데이터 프레임이 아닌 경우는 기본 출력 사용
    else:
        print(df)

    # 빈 줄 출력
    print("\n")


