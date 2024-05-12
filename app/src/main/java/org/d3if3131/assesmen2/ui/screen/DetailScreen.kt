package org.d3if3131.assesmen2.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3131.assesmen2.R
import org.d3if3131.assesmen2.database.TanamanDb
import org.d3if3131.assesmen2.model.Tanaman
import org.d3if3131.assesmen2.ui.theme.Assesmen2Theme
import org.d3if3131.assesmen2.util.ViewModelFactory
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = TanamanDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var nama by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf("") }
    var catatan by remember { mutableStateOf("") }
    var kondisi by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        if (id != null) return@LaunchedEffect
        tanggal = LocalDate.now().toString()
    }

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val tanaman: Tanaman = viewModel.getTanaman(id) ?: return@LaunchedEffect
        nama = tanaman.nama
        tanggal = tanaman.tanggal
        catatan = tanaman.catatan
        kondisi = tanaman.kondisi
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = colorResource(id = R.color.white)
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_tanaman))
                    else
                        Text(text = stringResource(id = R.string.edit_tanaman))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.primer),
                    titleContentColor = colorResource(id = R.color.white),
                    ),
                actions = {
                    if (id != null) {
                        DeleteAction { showDialog = true }
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ) {  padding ->
        Column(modifier = Modifier.padding(padding)) {
            FormMahasiswa(
                nama = nama,
                onNameChange = { nama = it },
                tanggal = tanggal,
                onTanggalChange = { tanggal = it },
                catatan = catatan,
                onCatatanChange = { catatan = it },
                selectedOption = kondisi,
                onOptionSelected = { kondisi = it },
                modifier = Modifier.weight(1f),
                onSave = { // Add a new onSave callback
                    if (nama == "" || tanggal == "" || catatan == "" || kondisi == "") {
                        Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                        return@FormMahasiswa
                    }
                    if (id == null) {
                        viewModel.insert(nama, tanggal, catatan, kondisi)
                    } else {
                        viewModel.update(id, nama, tanggal, catatan, kondisi)
                    }
                    navController.popBackStack()
                },
            )
        }
    }
}

@Composable
fun FormMahasiswa(
    nama: String, onNameChange: (String) -> Unit,
    tanggal: String, onTanggalChange: (String) -> Unit,
    catatan: String, onCatatanChange: (String) -> Unit,
    selectedOption: String, onOptionSelected: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = nama,
            onValueChange = { onNameChange(it) },
            label = { Text(text = stringResource(R.string.nama)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = tanggal,
            onValueChange = { onTanggalChange(it) },
            label = { Text(text = stringResource(R.string.isi_tanggal)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = catatan,
            onValueChange = { onCatatanChange(it) },
            label = { Text(text = stringResource(R.string.catatan)) },
            singleLine = false,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row {
        RadioButtonGroup(
            options = listOf("Belum Panen", "Siap Panen", "Sudah Panen"),
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected,
        )
        }
        Button( // Replace IconButton with Button
            onClick = onSave, // Use the onSave callback
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp), // Add some padding from radio buttons
            colors = ButtonDefaults.buttonColors(
                colorResource(id = R.color.primer),
                contentColor = colorResource(id = R.color.white)
            )
        ) {
            Text(text = stringResource(id = R.string.simpan)) // Set button text
        }
    }
}

@Composable
fun RadioButtonGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val outlineColor = MaterialTheme.colorScheme.outline
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = outlineColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    RadioButton(
                        selected = option == selectedOption,
                        onClick = { onOptionSelected(option) }
                    )
                    Text(
                        text = option,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = colorResource(id = R.color.white)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    Assesmen2Theme {
        DetailScreen(rememberNavController())
    }
}