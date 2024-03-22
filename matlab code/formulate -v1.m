extract_excel;

maxWeight=1000;
weightAccuracy=3;


elementAccuracy=0.02; %2 percent
elementsLength=length(elementsNames);
elementsToMatch=elementsLength;
materialsLength=length(prices);
elements=zeros(elementsLength,1);
elements(1)=2550;
elements(2)=18;
elements(3)=0.5;
elements(4)=0.4;
elements(5)=1550;

lb=minWeights;
ub=maxWeights;
f=prices;
Aeq=zeros(1,materialsLength);
beq=zeros(1,1);
A=zeros(elementsToMatch*2+2,materialsLength);
b=zeros(elementsToMatch*2+2,1);

b(1)=maxWeight*(elements(1)+elements(1)*elementAccuracy);
b(2)=maxWeight*(elements(2)+elements(2)*elementAccuracy);
b(3)=maxWeight*(elements(3)+elements(3)*elementAccuracy);
b(4)=maxWeight*(elements(4)+elements(4)*elementAccuracy);
b(5)=maxWeight*(elements(5)+elements(5)*elementAccuracy);

b(6)=-maxWeight*(elements(1)-elements(1)*elementAccuracy);
b(7)=-maxWeight*(elements(2)-elements(2)*elementAccuracy);
b(8)=-maxWeight*(elements(3)-elements(3)*elementAccuracy);
b(9)=-maxWeight*(elements(4)-elements(4)*elementAccuracy);
b(10)=-maxWeight*(elements(5)-elements(5)*elementAccuracy);

b(elementsToMatch*2+1)=maxWeight+weightAccuracy;
b(elementsToMatch*2+2)=-maxWeight+weightAccuracy;


A(1,1:60)=transpose(data(1:60,1));
A(2,1:60)=transpose(data(1:60,2));
A(3,1:60)=transpose(data(1:60,3));
A(4,1:60)=transpose(data(1:60,4));
A(5,1:60)=transpose(data(1:60,5));

A(6,1:60)=-transpose(data(1:60,1));
A(7,1:60)=-transpose(data(1:60,2));
A(8,1:60)=-transpose(data(1:60,3));
A(9,1:60)=-transpose(data(1:60,4));
A(10,1:60)=-transpose(data(1:60,5));

A(elementsToMatch*2+1,1:60)=ones(1,materialsLength);
A(elementsToMatch*2+2,1:60)=-ones(1,materialsLength);

[x, fval] = linprog(f,A,b,Aeq,beq,lb,ub);
weight=sum(x);
for d = 1:materialsLength
  fprintf('%6.3f \t%s\n',x(d))
end

fprintf('\n%6.3f \t%s\n',fval/weight)