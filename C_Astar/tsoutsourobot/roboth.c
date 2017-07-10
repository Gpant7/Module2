#include <stdio.h>
#include <stdlib.h>
#include <math.h>

typedef int state_type;
typedef int g_type;
typedef double h_type;

typedef struct Node{
	struct Node *parent;
	int x;
	int y;
	state_type state;
	g_type g;
	h_type h;
	h_type dist;
	int next; //0 open / 1 closed
	int pos;
	struct Node *r_sibling;
	struct Node *child1;
} SearchGraphNode;

int qel=0;
SearchGraphNode **queue;
SearchGraphNode *last_el;
SearchGraphNode *min;

void combine(int i){
int l,r,mp;
SearchGraphNode *temp;

l=2*i;
r=2*i + 1;
mp = i;

if (l<=qel && queue[l]->dist < queue[mp]->dist) mp=l;
if (r<=qel && queue[r]->dist < queue[mp]->dist) mp=r;
if (mp != i){
	temp = queue[i];
	queue[i] = queue[mp];
	queue[mp] = temp;
	queue[mp]->pos = mp;
	queue[i]->pos = i;	
	if (queue[mp]->pos == qel) last_el = queue[mp];
	if (queue[i]->pos == qel) last_el = queue[i];
	combine(mp);}
}

int q_min(void){
	if (qel == 0) return -1;
	min = queue[1];
	queue[1] = queue[qel];
	last_el->pos = 1;
	qel--;	
	combine(1);
	return 0;
}	
	
void insert(SearchGraphNode *el){
int i,p;
SearchGraphNode *temp;
	
	qel++;
	queue[qel] = el;
	el->pos = qel;	
	last_el = el;
	i = qel;
	p = i/2;

	while (i>1 && queue[p]->dist > queue[i]->dist){
		temp = queue[p];
		queue[p] = queue[i];
		queue[i] = temp;
		queue[p]->pos = p;
		queue[i]->pos = i;
		if (queue[p]->pos == qel) last_el = queue[p];
		if (queue[i]->pos == qel) last_el = queue[i];

		i=p;
		p=p/2;
	}
}

void move(int type,int cur_x,int cur_y,int *new_x,int *new_y){
	if (type == 0){//up
		*new_x = cur_x-1;
		*new_y = cur_y;
	}

	if (type == 1){//down
		*new_x = cur_x+1;
		*new_y = cur_y;
	}

	if (type == 2){//left
		*new_x = cur_x;
		*new_y = cur_y-1;
	}

	if (type == 3){//right
		*new_x = cur_x;
		*new_y = cur_y+1;
	}
}
double distance(int cur_x,int cur_y,int exitx,int exity){
	double temp;

	temp = (exitx - cur_x)*(exitx - cur_x) + (exity - cur_y)*(exity - cur_y);
	return sqrt(temp);
}
	
int main(void){
int i,j;
int rob1x,rob1y,rob2x,rob2y;
int x_max,y_max,exit_x,exit_y,newx,newy;
char temp;
int **xy_plane;//0 is O, 1 is X
double **dyn_rob1;//-1 undefined
double **dyn_rob2;
SearchGraphNode *root,*root2,*cur_node,*par,*new_node;
int *moves_x,*moves_y;
int num_moves = 0;
int temp_move;
int *moves_x2,*moves_y2;
int num_moves2 = 0;

scanf("%d %d",&x_max,&y_max);
scanf("%d %d",&rob1x,&rob1y);
scanf("%d %d",&rob2x,&rob2y);
scanf("%d %d",&exit_x,&exit_y);

i = x_max*y_max;
xy_plane = (int **) malloc((x_max+1)*sizeof(int *));
dyn_rob1 = (double **) malloc((x_max+1)*sizeof(double *));
dyn_rob2 = (double **) malloc((x_max+1)*sizeof(double *));
queue = (SearchGraphNode **) malloc((i+1)*sizeof(SearchGraphNode *)); //seira

for(i=1; i<=x_max; i++){
	xy_plane[i] = (int *) malloc((y_max+1)*sizeof(int));
	dyn_rob1[i] = (double *) malloc((y_max+1)*sizeof(double));
	dyn_rob2[i] = (double *) malloc((y_max+1)*sizeof(double));	
	
	scanf("%c",&temp); //gia to enter
	for(j=1; j<=y_max; j++) {
		scanf("%c",&temp);
		if (temp == 79) xy_plane[i][j] = 0;
		else xy_plane[i][j] = 1;
		dyn_rob1[i][j] = -1;
		dyn_rob2[i][j] = -1;
	}
}

dyn_rob1[rob1x][rob1y] = 0;
dyn_rob2[rob2x][rob1y] = 0;
//initialisation gia arxikh thesh
root = (SearchGraphNode *) malloc(sizeof(SearchGraphNode));
root->parent = NULL;
root->x = rob1x;
root->y = rob1y;
root->state = 1;
root->g = 0;
root->h = distance(rob1x,rob1y,exit_x,exit_y);
root->dist = root->h;
root->next = 0;
root->r_sibling = NULL;
cur_node = root;

while ((cur_node->x != exit_x) || (cur_node->y != exit_y)){
	par = cur_node;
	
	for (i=0; i<4; i++){

		move(i,par->x,par->y,&newx,&newy);
		printf("<Robot 1 considering new position <%d,%d> at step %d\n",newx,newy,(par->g + 1));
		if ((newx>0) && (newy>0) && (newx<=x_max) && (newy<=y_max))
			if (xy_plane[newx][newy] != 1){
				if (cur_node == par){		
					cur_node->child1 = (SearchGraphNode *) malloc(sizeof(SearchGraphNode));	
					new_node = cur_node->child1;}		
				else{ 
					cur_node->r_sibling = (SearchGraphNode *) malloc(sizeof(SearchGraphNode));
					new_node = cur_node->r_sibling;}
			
				new_node->parent = par;
				new_node->x = newx;
				new_node->y = newy;
				new_node->state = cur_node->state + 1;
				new_node->g = par->g + 1;
				new_node->h = distance(newx,newy,exit_x,exit_y) + (double) 10 * (abs(newx - exit_x) + abs(newy - exit_y));
				new_node->dist = new_node->h + (double) new_node->g;		
				new_node->r_sibling = NULL;
				new_node->child1 = NULL;
			
				if (dyn_rob1[newx][newy] == -1){//gia to kopsimo tou dunamikou
					new_node->next = 0;
					dyn_rob1[newx][newy] = new_node->g;
					insert(new_node); 			
					}
				else{
					if (dyn_rob1[newx][newy] > new_node->g){
						new_node->next = 0;
						dyn_rob1[newx][newy] = new_node->g;
						insert(new_node);}
					else new_node->next = 1;
				}
		
				cur_node = new_node;
			}
			else printf("<Robot 1 ignoring position <%d,%d> because of an obstacle.\n",newx,newy);
		else printf("<Robot 1 ignoring position <%d,%d> because it is out of bounds.\n",newx,newy);
	}
	
	j = q_min();
	if (j == -1){
		printf("Robot 1: destination unreachable\n");
		break;}
	cur_node = min;
	printf("<Robot 1 moving to new position <%d,%d> at step %d\n",cur_node->x,cur_node->y,cur_node->g);

}

if (j!=-1){
	num_moves = cur_node->g + 1;
	moves_x = (int *) malloc(num_moves*sizeof(int));
	moves_y = (int *) malloc(num_moves*sizeof(int));
	i=num_moves-1;
	while (cur_node != NULL){
		moves_x[i] = cur_node->x;
		moves_y[i] = cur_node->y;	
		cur_node = cur_node->parent;
		i--;
	}

	printf("\nPath of robot 1:\n");
	printf("Initial position = <%d,%d>\n",moves_x[0],moves_y[0]);
	for (i=1; i<num_moves; i++)
		printf("Position at step %d = <%d,%d>\n",i,moves_x[i],moves_y[i]);
}
printf("\n");

qel = 0;
root2 = (SearchGraphNode *) malloc(sizeof(SearchGraphNode));
root2->parent = NULL;
root2->x = rob2x;
root2->y = rob2y;
root2->state = 1;
root2->g = 0;
root2->h = distance(rob2x,rob2y,exit_x,exit_y);
root2->dist = root2->h;
root2->next = 0;
root2->r_sibling = NULL;
cur_node = root2;

while (1){
	par = cur_node;

	for (i=0; i<4; i++){

		move(i,par->x,par->y,&newx,&newy);
		printf("<Robot 2 considering new position <%d,%d> at step %d\n",newx,newy,(par->g + 1));
		
		if ((newx>0) && (newy>0) && (newx<=x_max) && (newy<=y_max))
			if (xy_plane[newx][newy] != 1){
				temp_move = par->g + 1;

				if ((moves_x[temp_move] == newx) && (moves_y[temp_move] == newy)) 
					 if ((newx != exit_x) || (newy != exit_y)){
						printf("<Robot 2 ignoring position <%d,%d> because robot 1 is currently there\n",newx,newy);
						continue;}					

				if (cur_node == par){		
					cur_node->child1 = (SearchGraphNode *) malloc(sizeof(SearchGraphNode));	
					new_node = cur_node->child1;}		
				else{ 
					cur_node->r_sibling = (SearchGraphNode *) malloc(sizeof(SearchGraphNode));
					new_node = cur_node->r_sibling;}
			
				new_node->parent = par;
				new_node->x = newx;
				new_node->y = newy;
				new_node->state = cur_node->state + 1;
				new_node->g = par->g + 1;
				new_node->h = distance(newx,newy,exit_x,exit_y) + (double) 10 * (abs(newx - exit_x) + abs(newy - exit_y));
				new_node->dist = new_node->h + (double) new_node->g;		
				new_node->r_sibling = NULL;
				new_node->child1 = NULL;
	
				if (dyn_rob2[newx][newy] == -1){// && xy_plane[newx][newy] != 1){ //gia to kopsimo tou dunamikou
					new_node->next = 0;
					dyn_rob2[newx][newy] = new_node->g;
					insert(new_node);}
				else{
					if (dyn_rob2[newx][newy] > new_node->g){
						new_node->next = 0;
						dyn_rob2[newx][newy] = new_node->g;
						insert(new_node);}
					else new_node->next = 1;
				}
		
				cur_node = new_node;
				
				
			}
			else printf("<Robot 2 ignoring position <%d,%d> because of an obstacle.\n",newx,newy);
		else printf("<Robot 2 ignoring position <%d,%d> because it is out of bounds.\n",newx,newy);

	}
	
	j = q_min();
	if (j == -1){
		printf("Robot 2: destination unreachable\n");
		break;}
	cur_node = min;
	printf("<Robot 2 moving to new position <%d,%d> at step %d\n",cur_node->x,cur_node->y,cur_node->g);
	if ((abs(cur_node->x - exit_x) == 1) && (abs(cur_node->y - exit_y) == 0)) break;
	if ((abs(cur_node->x - exit_x) == 0) && (abs(cur_node->y - exit_y) == 1)) break;
}

if (j!=-1){
	num_moves2 = cur_node->g + 1;			
	moves_x2 = (int *) malloc(num_moves2*sizeof(int));
	moves_y2 = (int *) malloc(num_moves2*sizeof(int));
	i=num_moves2-1;
	while (cur_node != NULL){
		moves_x2[i] = cur_node->x;
		moves_y2[i] = cur_node->y;	
		cur_node = cur_node->parent;
		i--;
	}

	printf("\nPath of robot 2:\n");
	printf("Initial position = <%d,%d>\n",moves_x2[0],moves_y2[0]);
	for (i=1; i<num_moves2; i++)
		printf("Position at step %d = <%d,%d>\n",i,moves_x2[i],moves_y2[i]);
}
free(queue);
free(xy_plane);
free(dyn_rob1);
free(dyn_rob2);
free(moves_x);
free(moves_y);
free(moves_x2);
free(moves_y2);

return 0;
}
