extract_excel;

elements=zeros(elementsLength,1);
elements(1)=2550;
elements(2)=18;
elements(3)=0.5;
elements(4)=0.4;
elements(5)=1550;

y=find_optimal(prices,minWeights,maxWeights,elements,data,matSelection,45,60,1000,3,0.02);
