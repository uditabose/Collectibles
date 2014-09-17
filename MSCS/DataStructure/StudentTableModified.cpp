#include <iostream>
#include <fstream>
#include <cstdlib>
#include <cmath>
#include <iomanip>

using namespace std;

/**
* Data computed from raw student data
*/
struct COMPUTED_STUDENT_DATA {

    // Sorted scores of 19 homework
    int SCORE_HOMEWORK_SORTED[19];

    // Homework grade
    float HQSCORE;

    // Average score
    float AVERAGE;

    // Grade of the student
    char GRADE;
};

/**
* A structure describing a student and her
* grades in 19 homeworks, one midterm and one final.
* Container for computed data, such as final grade, average
* etc.
*/
struct A_STUDENT {

    // First name of the student
    string FIRST_NAME;

    // Last name of the student
    string LAST_NAME;

    // Scores of 19 homework
    int SCORE_HOMEWORK[19];

    // Score of midterm exam
    int SCORE_MIDTERM;

    // Score of final exam
    int SCORE_FINAL;

    // Computed data for the student
    COMPUTED_STUDENT_DATA computedData;

};

// -----------------  Utility functions ---------------------------

/**
* Prints elements of an array separated by comma.
*/
template<typename T>
void printArray(T arr[], int n) {
    for(int i = 0; i < n; i++) {
        cout << arr[i] << ", ";
    }
    cout << endl;
}

/**
* Sorts an array of variables of type T in ascending order
* following insertion sort
*/
template<typename T>
void insertionSort(T anArray[], int length) {

    int fs = 0;
    for(int i = 1; i < length; i++) {
        fs = anArray[i];
        for(int j = i - 1; (j >= 0 && (anArray[j] > fs)); j--) {
            anArray[j + 1] = anArray[j];
            anArray[j] = fs;
        }

    }

}

/**
* Sorts an array of variables of type A_STUDENT in ascending order
* based on average score following insertion sort
*/
void insertionSort(A_STUDENT anArray[], int length) {

    for(int i = 1; i < length; i++) {
        A_STUDENT fs = anArray[i];
        for(int j = i - 1; (j >= 0 && (anArray[j].computedData.AVERAGE > fs.computedData.AVERAGE)); j--) {
            anArray[j + 1] = anArray[j];
            anArray[j] = fs;
        }

    }

}


/**
* Trims a string
*/
void trim(string& strToTrim)
{
  size_t nonWhiteCharPos = strToTrim.find_last_not_of(' ');

  if(nonWhiteCharPos != string::npos) {
    // Right trim
    strToTrim.erase(nonWhiteCharPos + 1);
    nonWhiteCharPos = strToTrim.find_first_not_of(' ');

    // Left trim
    if(nonWhiteCharPos != string::npos) {
        strToTrim.erase(0, pos);
    }
  }
  else {
    // A string with just whitespace
    strToTrim.erase(strToTrim.begin(), strToTrim.end());
  }
}
// -----------------  Utility functions ---------------------------

/**
* Function to find the best 18 scores of a student
* out of 19 homework scores
*/
int BEST_18(int STUDENT_HOMEWORK_GRADE[]) {
    int best18 = 0;
    for(int i = 18; i >= 0; i--) {
        best18 += STUDENT_HOMEWORK_GRADE[i];
    }
    return best18;
}

/**
* Function to find the homework quiz weighted score for grading,
* (best 18 out of 19) of a student
*/
float HQGRADE(int STUDENT_BEST_18_SCORE) {
    return (float)(((float)STUDENT_BEST_18_SCORE/180) * 100);
}

/**
* Function to find the average score of a student
* out of 19 homework, one midterm and one final exam
*/
float GET_AVERAGE(A_STUDENT& THE_STUDENT) {
    float average = 0.35 * (THE_STUDENT.SCORE_MIDTERM + THE_STUDENT.SCORE_FINAL)
                + 0.3 * (THE_STUDENT.computedData.HQSCORE);
    return round(average);
}

/**
* Function to find the final grade of a student
* out of 19 homework, one midterm and one final exam
*/
char GET_GRADE(float STUDENT_AVERAGE) {
    char grade;
    if(STUDENT_AVERAGE >= 90.0f && STUDENT_AVERAGE <= 100.0f) {
        grade = 'A';
    } else if(STUDENT_AVERAGE >= 80.0f && STUDENT_AVERAGE < 90.0f) {
        grade = 'B';
    } else if(STUDENT_AVERAGE >= 70.0f && STUDENT_AVERAGE < 80.0f) {
        grade = 'C';
    } else {
        grade = 'F';
    }


    return grade;
}

/**
* Convenience method : Sorts the array of homework scores
* of a student
*/
void sortStudentHomework(A_STUDENT& aStudent) {
    insertionSort(aStudent.SCORE_HOMEWORK, 19);
}

/**
* Convenience method : Computes the HQ Grade of a student
*/
void calculateHQGrade(A_STUDENT& aStudent) {
    int best18Score = BEST_18(aStudent.SCORE_HOMEWORK);
    aStudent.computedData.HQSCORE = HQGRADE(best18Score);
}

/**
* Convenience method : Computes the average score of a student
*/
void calculateAverage(A_STUDENT& aStudent) {
    aStudent.computedData.AVERAGE = GET_AVERAGE(aStudent);
}

/**
* Convenience method : Computes the grade student based on computed average
*/
void determineGrade(A_STUDENT& aStudent) {
    aStudent.computedData.GRADE = GET_GRADE(aStudent.computedData.AVERAGE);
}

/**
* Prints student data table header in desired format
*/
void printStudentDataHeader(ofstream& studentDataTableFStream) {

    studentDataTableFStream << "Name\t\t" << "Midterm\t"
                            << "HQavg\t" << "Final\t" << "Avg\t"
                            << "Grade" << endl;
    studentDataTableFStream << endl;
    studentDataTableFStream << endl;

}

/**
* Prints individual student data in desired format
*/
void printStudentData(ofstream& studentDataTableFStream, A_STUDENT& aStudent) {

    studentDataTableFStream << aStudent.LAST_NAME
                        << "," << aStudent.FIRST_NAME
                        << "\t" << aStudent.SCORE_MIDTERM
                        << "\t" << aStudent.computedData.HQSCORE
                        << "\t" << aStudent.SCORE_FINAL
                        << "\t" << setprecision(2) << aStudent.computedData.AVERAGE
                        << "\t" << aStudent.computedData.GRADE
                        << endl;


}

/**
* Reads input data and parses it to populate the
* student data in A_STUDENT struct instances
*/
int prepareStudentData(A_STUDENT THE_CLASS[]) {

    ifstream studentRawData("input1.txt");

    if(!studentRawData) {
        cerr << "Student data can not be read!" << endl;
        return -1;
    }

    int studentCounter = -1;
    int homeworkCounter = 0;
    bool isNextLineWithFinalScore = false;

    while(studentRawData) {
        string dataLine;
        getline(studentRawData, dataLine);

        if(dataLine.length() == 0) {
            continue;
        }

        size_t commaPos = dataLine.find((','));
        if(commaPos != string::npos) {
            // Line with name
            isNextLineWithFinalScore = true;
            homeworkCounter = 0;
            A_STUDENT &aStudent = THE_CLASS[++studentCounter];

            aStudent.FIRST_NAME = dataLine.substr(0, commaPos);
            aStudent.LAST_NAME = dataLine.substr(commaPos + 1);

        } else if(isNextLineWithFinalScore) {
            // Line with Midterm and Final scores
            isNextLineWithFinalScore = false;
            A_STUDENT &aStudent = THE_CLASS[studentCounter];
            size_t whitePos = dataLine.find((' '));

            if(whitePos != string::npos) {
                aStudent.SCORE_MIDTERM = atoi(dataLine.substr(0, whitePos).c_str());
                aStudent.SCORE_FINAL = atoi(dataLine.substr(whitePos + 1).c_str());
            }

        } else {
            // Line with Homework scores
            A_STUDENT &aStudent = THE_CLASS[studentCounter];
            int whitePos = 0, startPos = 0;

            // splitting the data line with whitespace
            while((whitePos = dataLine.find((' '), startPos)) != string::npos) {

                aStudent.SCORE_HOMEWORK[homeworkCounter++] =
                            atoi(dataLine.substr(startPos, whitePos - startPos).c_str());
                startPos = whitePos + 1;
            }

            // content after last whitespace
            aStudent.SCORE_HOMEWORK[homeworkCounter++] = atoi(dataLine.substr(startPos).c_str());
        }
    }


    return studentCounter + 1;

}

/**
* Find number of students in the array of students
* Sorts the homework array
* Calculates the best 18 of 19 homework scores
* Calculates the average score of each student
* Determines the grade of each student
*/
void computeStudentGrade(A_STUDENT THE_CLASS[], int noOfStudents) {

    for (int studentCount = 0; studentCount < noOfStudents; studentCount++) {
        A_STUDENT& aStudent = THE_CLASS[studentCount];

        sortStudentHomework(aStudent);
        calculateHQGrade(aStudent);
        calculateAverage(aStudent);
        determineGrade(aStudent);
    }

    insertionSort(THE_CLASS, noOfStudents);
}

/**
* Prints the formatted data in output file
*/
void printStudentTable(A_STUDENT THE_CLASS[], int noOfStudents) {

    ofstream studentDataTableFStream("output1.txt");

    if(!studentDataTableFStream) {
        cerr << "Can not create/open the output data file " << endl;
        return;
    }

    printStudentDataHeader(studentDataTableFStream);

    // Writing file in the reverse order as the array, so A graders come at top
    for (int studentCount = noOfStudents - 1; studentCount >= 0; studentCount--) {
        printStudentData(studentDataTableFStream, THE_CLASS[studentCount]);
    }
}


int main(){
    cout << "The Homework : Week One : START" << endl;

    A_STUDENT INIT_CLASS[25];

    int noOfStudents = prepareStudentData(INIT_CLASS);

    A_STUDENT THE_CLASS[noOfStudents];

    for(int stCnt = 0; stCnt < noOfStudents; stCnt++) {
        THE_CLASS[stCnt] = INIT_CLASS[stCnt];
    }

    computeStudentGrade(THE_CLASS, noOfStudents);

    printStudentTable(THE_CLASS, noOfStudents);

    cout << "The Homework : Week One : STOP" << endl;
}
