package com.example.villa.ui.view.villa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villa.model.Review
import com.example.villa.model.Villa
import com.example.villa.ui.customwidget.CostumeTopAppBar
import com.example.villa.ui.navigation.DestinasiNavigasi
import com.example.villa.ui.view.pelanggan.DestinasiDetaiPelanggan
import com.example.villa.ui.viewmodel.PenyediaViewModel
import com.example.villa.ui.viewmodel.villa.DetailVillaUiState
import com.example.villa.ui.viewmodel.villa.DetailVillaViewModel

object DestinasiDetailVilla : DestinasiNavigasi {
    override val route = "detailvilla"
    override val titleRes = "Detail Villa"
    const val ID_Villa = "id_villa"
    val routeWithArg = "$route/{$ID_Villa}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onEditClick: (Int) -> Unit,
    navigateBack: () -> Unit,
    onDeleteClick: (Villa) -> Unit = {},
    navigateToInsertReservasi: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailVillaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.villaDetailState
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailVilla.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (uiState) {
                    is DetailVillaUiState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )

                    is DetailVillaUiState.Error -> Text(
                        text = "Gagal memuat data.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    is DetailVillaUiState.Success -> {
                        val villa = uiState.villa
                        val review = uiState.review // Mengambil daftar review

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Informasi Villa
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Id Villa: ${villa.id_villa}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Nama Villa: ${villa.nama_villa}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Alamat: ${villa.alamat}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Kamar Tersedia: ${villa.kamar_tersedia}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                            // Tombol Edit
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(onClick = { onEditClick(villa.id_villa) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF00BFFF),
                                        contentColor = Color.White
                                )) {
                                    Text("Edit Villa")
                                }
                                Button(onClick = {
                                    viewModel.deleteVilla(
                                        idVilla = villa.id_villa,
                                        onSuccess = {
                                            onBackClick()
                                        },
                                        onError = {}
                                    )
                                },colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF00BFFF),
                                    contentColor = Color.White
                                )
                                ) {
                                    Text("Hapus Villa")
                                }
                                Button(onClick = navigateToInsertReservasi,
                                    colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF00BFFF),
                                    contentColor = Color.White
                                )) {
                                    Text("Reservasi Villa")
                                }
                            }

                            // Bagian Review
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                elevation = CardDefaults.cardElevation(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Reviews",
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    if (review.isNotEmpty()) {
                                        review.forEach { rev ->
                                            Card(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 4.dp),
                                                elevation = CardDefaults.cardElevation(4.dp)
                                            ) {
                                                Column(
                                                    modifier = Modifier.padding(16.dp),
                                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                                ) {
                                                    Text(
                                                        text = "ID Review: ${rev.id_review}",
                                                        style = MaterialTheme.typography.titleMedium
                                                    )
                                                    Text(
                                                        text = "ID Reservasi: ${rev.id_reservasi}",
                                                        style = MaterialTheme.typography.bodyMedium
                                                    )
                                                    Text(
                                                        text = "Rating: ${rev.nilai}",
                                                        style = MaterialTheme.typography.bodyMedium
                                                    )
                                                    Text(
                                                        text = rev.komentar ?: "Tidak ada komentar",
                                                        style = MaterialTheme.typography.bodySmall
                                                    )
                                                }
                                            }
                                        }
                                    } else {
                                        Text(
                                            text = "Belum ada Review",
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}


