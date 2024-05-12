package org.d3if3131.assesmen2.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.d3if3131.assesmen2.R
import org.d3if3131.assesmen2.ui.theme.Assesmen2Theme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CekScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.asesmen1),
                            tint = colorResource(id = R.color.white)
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.kembali))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.primer),
                    titleContentColor = colorResource(id = R.color.white),
                )
            )
        }
    ) { padding ->
        CekContent(Modifier.padding(padding))
    }
}



@SuppressLint("StringFormatInvalid")
@Composable
fun CekContent(modifier: Modifier) {

    var nama by rememberSaveable { mutableStateOf("") }
    var namaError by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    var perkiraanPanen by rememberSaveable { mutableStateOf("") }
    var pickedDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var pickedDateError by rememberSaveable { mutableStateOf(false) }
    var masaTanam by rememberSaveable { mutableIntStateOf(0) }
    val dateDialogState = rememberMaterialDialogState()

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofLocalizedDate( FormatStyle.SHORT)
                .format(pickedDate)
        }
    }

    val radioOptions = listOf(
        stringResource(id = R.string.jagung),
        stringResource(id = R.string.cabai)
    )

    var jenis by rememberSaveable { mutableStateOf(radioOptions[0]) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(
                    width = 2.dp,
                    color = colorResource(id = R.color.primer),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
                .clickable(onClick = {
                }),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.primer))
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.intro),
                    style = MaterialTheme.typography.labelLarge,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.weight(1f)
                )
//                Spacer(modifier = Modifier.weight(0.0f))
                Image(
                    painter = painterResource(id = R.mipmap.icon_panen),
                    contentDescription = stringResource(id = R.string.cek_tanggal_panen),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(width = 110.dp, height = 110.dp)
                )

            }
        }
        OutlinedTextField(
            value = nama ,
            onValueChange = {nama = it
                namaError = false},
            label = { Text(text = stringResource(R.string.nama)) },
            isError = namaError,
            trailingIcon = { IconPicker(namaError, "") },
            supportingText = { ErrorHint(namaError)},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row (
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ){
            radioOptions.forEach { text ->
                JenisOption(
                    label = text,
                    isSelected = jenis == text,
                    modifier = Modifier
                        .selectable(
                            selected = jenis == text,
                            onClick = {
                                jenis = text
                                perkiraanPanen = ""
                                namaError = false
                            },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp)

                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                readOnly = true,
                value = formattedDate,
                onValueChange = {},
                label = { Text(text = stringResource(R.string.pick_date)) },
                isError = pickedDateError,
                trailingIcon = {
                    IconPicker(isError = pickedDateError, unit = "")
                    IconButton(onClick = { dateDialogState.show() }) {
                        Icon(Icons.Rounded.DateRange, contentDescription = stringResource(id = R.string.pilih_tanggal))
                    } },
                supportingText = { ErrorHint(pickedDateError) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(text = stringResource(id = R.string.pilih)) {
                        Toast.makeText(
                            context,
                            R.string.tanggal_dipilih,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    negativeButton(text = stringResource(id = R.string.cancel))
                }
            ) {
                datepicker(
                    initialDate = LocalDate.now(),
                    title = stringResource(id = R.string.pilih_tanggal),
                ) {
                    pickedDate = it
                }
            }
        }
        Button(
            onClick = {
                namaError = (nama == "" || nama == "0")
                pickedDateError = (pickedDate.toString().isEmpty())
                if (pickedDateError && namaError) return@Button

                val isCorn = jenis == radioOptions[0]
                masaTanam = getMasaTanam(isCorn)

                val faktorKoreksi = 1.0

                val tanggalPanen = LocalDate.parse(formattedDate, DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
                    .plusDays((masaTanam * faktorKoreksi).toInt().toLong())

                if (nama != "") {
                    perkiraanPanen = formatDate(tanggalPanen)
                }
            },
            modifier = Modifier
                .padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                colorResource(id = R.color.primer),
            )
        ) {
            Text(text = stringResource(id = R.string.cek))
        }

        if (perkiraanPanen.isNotEmpty()) {
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp
            )
            Text(text = stringResource(id = R.string.tanggal_panen, perkiraanPanen))

            Button(
                onClick = {
                    shareData(
                        context = context,
                        message = context.getString(R.string.bagikan_hasil,
                            nama, jenis))
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal=32.dp, vertical=16.dp),
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.primer),
                )
            ) {
                Text(text = stringResource(R.string.bagikan))
            }
        }
    }
}

@Composable
fun JenisOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

//Masa tanam umumnya untuk jagung dan cabai
private fun getMasaTanam(isCorn: Boolean): Int {
    val masaTanamValue = if (isCorn) {
        90
    } else {
        75
    }
    return masaTanamValue
}


private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}

private fun formatDate(date: LocalDate): String {
    return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(date)
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun CekPreview() {
    Assesmen2Theme {
        CekScreen(rememberNavController())
    }
}