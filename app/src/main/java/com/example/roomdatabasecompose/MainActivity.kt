package com.example.roomdatabasecompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.roomdatabasecompose.database.StudentDatabase
import com.example.roomdatabasecompose.model.Students
import com.example.roomdatabasecompose.ui.theme.RoomDatabaseComposeTheme
import com.example.roomdatabasecompose.viewmodel.StudentViewModel
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel: StudentViewModel by viewModels()
        setContent {
            RoomDatabaseComposeTheme {
                StudentList(viewModel)
            }
        }
    }
}

@Composable
fun DataForm(onSave: (Students) -> Unit) {
    var name by remember { mutableStateOf("") }
    var section by remember { mutableStateOf("") }
    var rollNo by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.padding(10.dp))

        Text("Enter New Student", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.padding(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Name: ",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.width(60.dp)
            )
            TextField(
                value = name,
                onValueChange = { newName ->
                    name = newName
                },
                label = { Text("Enter Name", style = MaterialTheme.typography.labelMedium) },
                modifier = Modifier.width(230.dp),
                singleLine = true
            )
        }

        Spacer(Modifier.padding(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Section: ",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.width(60.dp)
            )
            TextField(
                value = section, onValueChange = { newSection ->
                    section = newSection
                },
                label = { Text("Enter Section", style = MaterialTheme.typography.labelMedium) },
                modifier = Modifier.width(230.dp),
                singleLine = true
            )
        }

        Spacer(Modifier.padding(10.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "RollNo: ",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.width(60.dp)
            )
            TextField(
                value = rollNo, onValueChange = { newRollNo ->
                    rollNo = newRollNo
                },
                label = { Text("Enter RollNo.", style = MaterialTheme.typography.labelMedium) },
                modifier = Modifier.width(230.dp),
                singleLine = true
            )
        }

        Spacer(Modifier.padding(10.dp))

        Button(onClick = {
            if (name.isNotBlank() && section.isNotBlank() && rollNo.isNotBlank()) {
                val newStudents = Students(0, name, section, rollNo)
                onSave(newStudents)
                name = ""; section = ""; rollNo = ""
            }
            Firebase.analytics.logEvent("save_btn_click",null)
        }, Modifier.width(100.dp)) {
            Text("Save")
        }
    }
}

@Composable
fun StudentList(viewModel: StudentViewModel) {
    val students by viewModel.allStudents.collectAsState(initial = emptyList())

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        DataForm(onSave = { viewModel.saveStudent(it) })

        Spacer(modifier = Modifier.height(16.dp))
        Text("Saved Students", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(students) { student ->
                StudentItem(student)
                Spacer(Modifier.padding(5.dp))
            }
        }

    }
}

@Composable
fun StudentItem(students: Students) {
    Column(
        Modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(10.dp)
            .width(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "Name: ${students.name}",
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
        Text(
            text = "Section: ${students.section}",
            style = MaterialTheme.typography.labelMedium,
            color = Color.White
        )
        Text(
            text = "Roll No: ${students.rollNo}",
            style = MaterialTheme.typography.labelMedium,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}